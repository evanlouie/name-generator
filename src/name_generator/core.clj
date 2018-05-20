(ns name-generator.core
  (:require [clojure.string :as s])
  (:gen-class))

(def read-file (memoize
                (fn [filename]
                  (slurp (clojure.java.io/resource filename)))))

(defn get-last-names []
  (let [census-string (read-file "Names_2010Census.csv")
        lines (s/split-lines census-string)
        keys (map #(keyword %) (s/split (first lines) #","))
        values (map #(s/split % #",") (rest lines))
        census (map #(zipmap keys %) values)
        last-names (map #(s/capitalize (get % :name)) census)]
    last-names))

(defn get-first-names []
  (letfn [(get-name-from-line [line]
            (s/capitalize (first (s/split line #"\s"))))
          (get-names-from-string [file-string]
                                 (map get-name-from-line
                                      (s/split-lines file-string)))]
    (let [male-names-string (read-file "dist.male.first.txt")
          female-names-string (read-file "dist.female.first.txt")
          male-names (get-names-from-string male-names-string)
          female-names (get-names-from-string female-names-string)]
      (concat male-names female-names))))

(defn generate-name []
  (let [first-name (rand-nth (get-first-names))
        last-name (rand-nth (get-last-names))]
    (s/join " " [first-name last-name])))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (generate-name)))

