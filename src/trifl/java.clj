(ns trifl.java
  (:require [clojure.java.io :as io]
            [clojure.pprint :refer [print-table]]
            [clojure.reflect :refer [reflect]])
  (:import [java.util.UUID]
           [java.lang.management ManagementFactory]))

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

(defn dump-all-threads
  ([]
    (dump-all-threads (ManagementFactory/getThreadMXBean)))
  ([thread-bean]
    (dump-all-threads thread-bean true true))
  ([thread-bean locked-monitors locked-synchronizers]
    (.dumpAllThreads thread-bean locked-monitors locked-synchronizers)))

(defn thread->text
  [thread-info]
  (str thread-info))

(defn dump-threads
  ([]
    (dump-threads (dump-all-threads)))
  ([threads]
    (->> threads
         (into [])
         (map thread->text))))
