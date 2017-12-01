(ns trifl.dev
  (:require [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.pprint :refer [pprint print-table]]
            [clojure.string :as string]
            [clojure.walk :refer [macroexpand-all]]
            [trifl.core :as core]
            [trifl.docs :as docs]
            [trifl.fs :as fs]
            [trifl.java :as java]
            [trifl.meta :as meta]
            [trifl.net :as net]
            [trifl.ps :as process]))

(def show-methods #'trifl.java/show-methods)
