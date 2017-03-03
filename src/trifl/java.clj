(ns trifl.java
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [print-table]]
            [clojure.reflect :refer [reflect]])
  (:import [java.util.UUID]))

(defn add-shutdown-handler [func]
  (.addShutdownHook (Runtime/getRuntime)
                    (Thread. func)))

(defn join-current-thread []
  (.join (Thread/currentThread)))

(defn show-methods
  "Display a Java object's public methods."
  [obj]
  (print-table
    (sort-by :name
      (filter (fn [x]
                (contains? (:flags x) :public))
              (:members (reflect obj))))))

(defn uuid4 []
  (str (java.util.UUID/randomUUID)))
