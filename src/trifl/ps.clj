(ns trifl.ps
  (:require
    [clojure.java.shell :as shell]
    [clojure.string :as string]
    [trifl.core :as core]))

(defn get-pid
  "Linux/Mac only!"
  [process]
  (let [process-field (.getDeclaredField (.getClass process) "pid")]
    (.setAccessible process-field true)
    (.getInt process-field process)))

;;;   Process Table Data Structure   ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- output-format->keys
  [output-fields]
  (->> #","
       (string/split output-fields)
       (mapv (comp keyword string/trim))))

(defn- parse-output-line
  [output-format output-line]
  (case output-format
    "pid,ppid,pgid,comm"
    (let [[pid ppid pgid & cmd] (string/split (string/trim output-line) #"\s+")]
      (conj
        (mapv core/->int [pid ppid pgid])
        (string/join " " cmd)))))

(defn- output-line->map
  [output-format output-line]
  (zipmap (output-format->keys output-format)
          (parse-output-line output-format output-line)))

(defn- output-lines->ps-info
  [output-format output-lines]
  (map (partial output-line->map output-format) output-lines))

(defn get-ps-info
  ([]
    (get-ps-info "pid,ppid,pgid,comm"))
  ([output-format]
    (->> output-format
         (shell/sh "ps" "-eo")
         :out
         (string/split-lines)
         (output-lines->ps-info output-format))))

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
