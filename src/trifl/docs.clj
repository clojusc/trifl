(ns trifl.docs
  (:require [clojure.string :as string]
            [trifl.meta :as meta])
  (:import (clojure.lang Symbol)
           (clojure.lang Namespace)
           (clojure.lang IFn)))

(defn- cleanup-markdown
  "Removes all Markdown backtick characters."
  [docstring]
  (string/replace docstring #"`" ""))

(defn- format-docstring
  [docstring]
  (->> docstring
       cleanup-markdown
       (format "\n%s\n\n")))

(defmulti get-docstring
  "Extract the docstring indicated by the given namespace and function name.

  The `an-ns` argument may be either a symbol for a namespace, a namespace
  object, or a function in a namespace (from which the namespace will be
  extracted)."
  (fn [ns-or-fn & args] (type ns-or-fn)))

(defmethod get-docstring Symbol
  [an-ns fn-name]
  (meta/get-meta an-ns [fn-name :doc]))

(defmethod get-docstring Namespace
  [an-ns fn-name]
  (get-docstring (ns-name an-ns) fn-name))

(defmethod get-docstring IFn
  [fun]
  (if-let [metadata (clojure.core/meta fun)]
    (:doc metadata)
    (str "ERROR: no metadata found for " fun)))

(defn get-formatted-docstring
  ([fun]
    (-> fun
        (get-docstring)
        (format-docstring)))
  ([an-ns fn-name]
    (->> fn-name
         (get-docstring an-ns)
         (format-docstring))))

(defn print-docstring
  ([fun]
    (print (get-formatted-docstring fun)))
  ([an-ns fn-name]
    (print (get-formatted-docstring an-ns fn-name))))
