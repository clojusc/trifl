(ns trifl.xml-tests
  (:require
    [clojure.test :refer :all]
    [clojure.data.xml :as xml]
    [trifl.xml :as xml-util]))

(defn sample-xml
  []
  (xml/emit-str
    (xml/sexp-as-element
      [:things [:thing {:name "1"}]
               [:thing {:name "2"}]])))

(def expected-without-pretty
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?><things>"
       "<thing name=\"1\"></thing><thing name=\"2\"></thing>"
       "</things>"))

(def expected-with-pretty
  (str "<?xml version=\"1.0\" encoding=\"UTF-8\"?><things>\n"
       "  <thing name=\"1\"/>\n  <thing name=\"2\"/>\n"
       "</things>\n"))

(deftest pretty-xml
  (is (= expected-without-pretty (sample-xml)))
  (is (= expected-with-pretty (xml-util/pretty-xml (sample-xml)))))
