(ns name-generator.core
  (:require [clojure.string :as string])
  (:gen-class))

(defrecord Name [^String fname ^String lname])

(def read-resource
  ^{:private true
    :doc "Read a file from project resources directory and return as string; Result is memoized"}
  (memoize
   (fn [filename]
     (slurp (clojure.java.io/resource filename)))))

(defn- get-last-names
  "Parse the 2010 Census CSV and return a distinct sequence of last names"
  []
  (let [census-string (read-resource "Names_2010Census.csv")
        lines (string/split-lines census-string)
        keys (map #(keyword %) (string/split (first lines) #","))
        values (map #(string/split % #",") (rest lines))
        census (map #(zipmap keys %) values)
        last-names (map #(string/capitalize (get % :name)) census)]
    (distinct last-names)))

(defn- get-first-names
  "Parse the 1990 male and female first names files and return a distinct sequence"
  []
  (letfn [(get-name-from-line [line]
            (string/capitalize (first (string/split line #"\s"))))
          (get-names-from-string [file-string]
                                 (map get-name-from-line
                                      (string/split-lines file-string)))]
    (let [male-names-string (read-resource "dist.male.first.txt")
          female-names-string (read-resource "dist.female.first.txt")
          male-names (get-names-from-string male-names-string)
          female-names (get-names-from-string female-names-string)]
      (distinct (concat male-names female-names)))))

(defn generate-name
  "Generate a random Name"
  ^Name
  []
  (let [first-name (rand-nth (get-first-names))
        last-name (rand-nth (get-last-names))]
    (Name. first-name last-name)))

(defn -main
  "I generate a random name"
  [& args]
  (let [name (generate-name)
        fname (:fname name)
        lname (:lname name)
        full-name (str fname " " lname)]
    (println full-name)))

