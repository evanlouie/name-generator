(ns name-generator.core
  (:require [name-generator.generator :refer [name-sequence]]
            [clojure.string :as string])
  (:import [name_generator.generator Name])
  (:gen-class))

(defn -main
  "I generate a random name"
  [& args]
  (do
    (println "How many names should I generate?")
    (def n (Integer/parseInt (read-line)))
    (def names (take n (name-sequence)))
    (->> names
         (map (fn [^Name name]
                (let [full-name (str (:fname name) " " (:lname name))]
                  (do (println full-name)
                      full-name))))
         (doall))
    (shutdown-agents)))
