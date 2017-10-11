(ns trifl.meta
  (:import (clojure.lang Namespace)))

(defn get-metas
  "Get metadata for a namespace."
  [^Namespace an-ns]
  (->> an-ns
       (ns-publics)
       (map (fn [[k v]] [k (meta v)]))
       (into {})))

(defn get-meta
  "Takes the same form as the general `get-in` function:

  ```
  (get-meta 'my.name.space ['my-func :doc])
  ```"
  [an-ns nss]
  (-> an-ns
      (get-metas)
      (get-in nss)))
