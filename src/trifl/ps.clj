(ns trifl.ps
  (:require
    [clojure.java.shell :as shell]
    [clojure.string :as string]
    [trifl.core :as core]))

;;;   Single Process Functions   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-pid
  "Linux/Mac only!"
  [process]
  (let [process-field (.getDeclaredField (.getClass process) "pid")]
    (.setAccessible process-field true)
    (.getInt process-field process)))

;;;   Process Table Data Structure   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- pid-str->pid-int
  [pid]
  (try
    (core/->int pid)
    (catch NumberFormatException ex
      "")))

(defn- percent->float
  [percent]
  (try
    (new Float percent)
    (catch NumberFormatException ex
      "")))

(defn- output-format->keys
  [output-fields]
  (->> #","
       (string/split output-fields)
       (mapv (comp keyword string/trim))))

(defn- split-fields
  [output-line]
  (string/split (string/trim output-line) #"\s+"))

(defn- parse-output-line
  [output-format output-line]
  (case output-format
    "%cpu,%mem"
    (let [[cpu mem] (split-fields output-line)]
      (mapv percent->float [cpu mem]))

    "pid,ppid,pgid,comm"
    (let [[pid ppid pgid & cmd] (split-fields output-line)]
      (conj
        (mapv pid-str->pid-int [pid ppid pgid])
        (string/join " " cmd)))))

(defn- output-line->map
  [output-format output-line]
  (zipmap (output-format->keys output-format)
          (parse-output-line output-format output-line)))

(defn- output-lines->ps-info
  [output-format output-lines]
  (map (partial output-line->map output-format) output-lines))

(defn parse-ps-info
  [output-format result]
  (->> result
       :out
       (string/split-lines)
       (output-lines->ps-info output-format)))

(defn get-ps-info
  ([]
    (get-ps-info "pid,ppid,pgid,comm"))
  ([output-format]
    (->> output-format
         (shell/sh "ps" "-eo")
         (parse-ps-info output-format)))
  ([output-format pid]
    (->> output-format
         (shell/sh "ps" "-p" (str pid) "-o")
         (parse-ps-info output-format)
         last)))

;;;   Process Descendants   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- -get-descendants
  [ps-info acc ps-parents]
  (if (seq ps-parents)
    (for [pid ps-parents]
      (let [child-pids (mapv :pid (filter #(= (:ppid %) pid) ps-info))]
        (-get-descendants ps-info (concat acc child-pids) child-pids)))
    acc))

(defn get-descendants
  [starting-pid]
  (let [ps-info (get-ps-info)]
    (flatten (-get-descendants ps-info [] [starting-pid]))))
