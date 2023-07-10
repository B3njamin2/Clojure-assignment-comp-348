(ns menu)

(defn display-menu []
  (println "*** SIS Menu ***\n"
           "------------------\n"
           "1. Display Courses\n"
           "2. Display Students\n"
           "3. Display Grades\n"
           "4. Display Student Record\n"
           "5. Calculate GPA\n"
           "6. Course Average\n"
           "7. Exit\n"
           "Enter an option? ")
  (flush))

