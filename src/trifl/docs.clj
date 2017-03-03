(ns trifl.docs
  (:require [clojure.string :as string]
            [trifl.meta :as meta]))

(defn get-docstring
  "Extract the docstring indicated by the given namespace and function name."
  [an-ns fn-name]
  (-> an-ns
      (meta/get-meta [fn-name :doc])
      ;; XXX make the following optional ... default false strip markdown for code
      (string/replace #"`" "")))

(defn format-docstring
  [an-ns fn-name]
  (->> fn-name
       (get-docstring an-ns)
       (format "\n%s\n\n")))

(defn print-docstring
  [an-ns fn-name]
  (print (format-docstring an-ns fn-name)))
