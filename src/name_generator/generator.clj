(ns name-generator.generator
  (:require [clojure.string :as string]))

(defrecord Name [^String fname ^String lname])

(defn- read-resource
  "Read a file from project resources directory and return as string."
  ^String
  [^String filename]
  (slurp (clojure.java.io/resource filename)))

(defn- get-last-names
  "Parse the 2010 Census CSV and return a distinct sequence of last names."
  ^clojure.lang.LazySeq
  []
  (let [census-string (read-resource "Names_2010Census.csv")
        lines (string/split-lines census-string)
        keys (map #(keyword %) (string/split (first lines) #","))
        values (map #(string/split % #",") (rest lines))
        census (map #(zipmap keys %) values)
        last-names (map #(string/capitalize (get % :name)) census)]
    (distinct last-names)))

(defn- get-first-names
  "Parse the 1990 male and female first names files and return a distinct sequence."
  ^clojure.lang.LazySeq
  []
  (letfn [(get-name-from-line [^String line]
            (string/capitalize (first (string/split line #"\s"))))
          (get-names-from-string [^String file-string]
                                 (map get-name-from-line
                                      (string/split-lines file-string)))]
    (let [names-strings (pmap read-resource ["dist.male.first.txt" "dist.female.first.txt"])
          names (flatten (pmap get-names-from-string names-strings))]
      (distinct names))))

(def ^:private ^clojure.lang.LazySeq first-names
  "Memoized list of male and female first names."
  (memoize (fn [] (get-first-names))))

(def ^:private ^clojure.lang.LazySeq last-names
  "Memoized list of last names."
  (memoize (fn [] (get-last-names))))

(defn generate-name
  "Generate a random Name"
  ^Name
  []
  (let [first-name (future (rand-nth (first-names)))
        last-name (future (rand-nth (last-names)))]
    (Name. @first-name @last-name)))

(defn name-sequence
  "Lazy list of Names"
  ^clojure.lang.LazySeq
  []
  (lazy-seq (cons (generate-name) (name-sequence))))

