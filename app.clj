(ns app 
  (:gen-class)
  (:require [db])
  (:require [menu]))

(def studentDB (db/optimizeData (db/parse-data "studs.txt" db/parse-student) "student"))
(def courseDB (db/optimizeData (db/parse-data "courses.txt" db/parse-course) "course"))
(def gradeDB (db/optimizeData (db/parse-data "grades.txt" db/parse-grade) "grade"))



(defn studRec []
  (println "Enter Student ID: ")
  (flush)
  (let [id (read-line)]
    (if (contains? studentDB (keyword id))
      (do 
        (println [id , (:name ((keyword id) studentDB))])
        (let [studGrades (filter #(= id (:studID %)) gradeDB)]
          (doseq [studGrade studGrades]
             (let [ courseInfo ((keyword (:courseID studGrade)) courseDB)]
               (println [(str (:courseName courseInfo) " " (:courseNo courseInfo)),
                         (:desc courseInfo)
                         (:semester studGrade)
                         (:grade studGrade)])))))
      (println "ID does not Exist"))))


(defn studGPA []
  (println "Enter Student ID: ")
  (flush)
  (let [id (read-line)]
    (if (contains? studentDB (keyword id))
      (let [studGrades (filter #(= id (:studID %)) gradeDB)
            gpa ( / (reduce #(+ %1 (get db/gpa  (:grade %2))) 0 studGrades) (count studGrades))]
        (println [id, (:name ((keyword id) studentDB)), "gpa:", gpa]))
      (println "ID does not Exist")))
)

(defn classGPA []
  (doseq [each (distinct (map #(select-keys % [:courseID :semester])gradeDB))]
    (let [classRec (filter #(and (= (:courseID each) (:courseID %)) (= (:semester each) (:semester %))) gradeDB)
          gpa (/ (reduce #(+ %1 (get db/gpa  (:grade %2))) 0 classRec) (count classRec))]
       (println [(str (:courseName ((keyword (:courseID each)) courseDB)) " " (:courseNo ((keyword (:courseID each)) courseDB))), (:semester each),"GPA:" gpa])
      )) 
  )


(defn app []
  (menu/display-menu)
  (let [option (read-line)]
    (case option
      "1" (do (db/printdb studentDB) (app))
      "2" (do (db/printdb courseDB) (app))
      "3" (do (db/printdb gradeDB) (app))
      "4" (do (studRec) (app))
      "5" (do (studGPA) (app))
      "6" (do (classGPA) (app))
      "7" (println "Exiting menu.")
      (do (println "Invalid option! Please enter number between [1-7]") (app)))))

(defn -main [& args]
   (app))

(-main)


