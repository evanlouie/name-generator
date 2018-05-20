(defproject name-generator "0.1.0-SNAPSHOT"
  :description "Random name generator based on 2010 US Census data"
  :url "https://github.com/evanlouie/name-generator.git"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]]
  :main ^:skip-aot name-generator.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
