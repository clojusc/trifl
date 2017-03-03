(ns trifl.fs
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [trifl.core :refer [sys-prop]]
            [trifl.java :refer [uuid4]]))

(defn dir?
  [fs-obj]
  (.isDirectory fs-obj))

(defn exists?
  [fs-obj]
  (.exists fs-obj))

(defn file?
  [fs-obj]
  (.isFile fs-obj))

(defn dir-exists?
  [fs-obj]
  (and (dir? fs-obj)
       (exists? fs-obj)))

(defn file-exists?
  [fs-obj]
  (and (file? fs-obj)
       (exists? fs-obj)))

(defn abs-path
  [fs-obj]
  (.getAbsolutePath fs-obj))

(defn expand-home
  [^String dir]
  (if (.startsWith dir "~")
    (->> "user.home"
         (sys-prop)
         (string/replace-first dir "~"))
    dir))

(defn mk-tmp-dir!
  "Creates a unique temporary directory on the filesystem under the
  JVM tmpdir."
  ([]
    (mk-tmp-dir! "ngap-nowa-clj-"))
  ([prefix]
    (let [base-dir (sys-prop "java.io.tmpdir")
          tmp-path-obj (io/file base-dir (str prefix (uuid4)))]
      (io/make-parents tmp-path-obj)
      (abs-path tmp-path-obj))))
