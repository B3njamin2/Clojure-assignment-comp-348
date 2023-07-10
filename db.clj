(ns db 
  (:require [clojure.string :as string]))

(def gpa {"A+" 4.3, "A" 4, "A-" 3.7,
          "B+" 3.3, "B" 3, "B-" 2.7,
          "C+" 2.3, "C" 2, "C-" 1.7,
          "D+" 1.3, "D" 1, "D-" 0.7,
          "F" 0}
)


(defn parse-grade [line]
  (let [[studID courseID semester grade] (string/split line #"\|")]
    {:studID studID
     :courseID courseID
     :semester semester
     :grade grade}))

(defn parse-course [line]
  (let [[id courseName courseNo credits desc] (string/split line #"\|")]
    {:id id
     :courseName courseName
     :courseNo courseNo
     :credits (read-string credits)
     :desc desc}))


(defn parse-student [line]
  (let [[studID name address phoneNumber] (string/split line #"\|")]
    {:studID  studID
     :name name
     :address address
     :phoneNumber phoneNumber}))


(defn parse-data [file parse-fn]
  (let [data (slurp file)]
    (map parse-fn (string/split-lines data))))

(defn optimizeData [parsed-data type]
  (condp = type
    "student"
     (into {} (map (fn [studentData] [(keyword (:studID studentData)) studentData]) parsed-data))
    "course"
    (into {} (map (fn [courseData] [(keyword (:id courseData)) courseData]) parsed-data))
    "grade"
    (into [] parsed-data)))

(defn printdb [map]
 (if (map? map)
   (doseq [elem map]
      (println (vec (vals (second elem)))))
   (doseq [elem map]
     (println (vec (vals elem)))))
)