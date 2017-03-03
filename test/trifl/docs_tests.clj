(ns trifl.docs-tests
  (:require [clojure.test :refer :all]
            [trifl.docs :as docs]))

(defn sample-docstring-func
  "Hey there, I'm a func."
  [])

(deftest docstring
  (is (= (docs/get-docstring
           'trifl.docs-tests 'sample-docstring-func)
         "Hey there, I'm a func.")))
