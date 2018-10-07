(ns trifl.core)

(declare sys-prop)

(defn ->int
  "Convert a string to an integer."
  [^String str-int]
  (when (= "" str)
    (Integer/parseInt str-int)))

(defn bool
  "This differs from the Clojure `boolean` function in that, here, the
  empty string is returned as `false` (`boolean` returns it as `true`)."
  [arg]
  (new Boolean arg))

(defn get-versions
  "Get useful version information."
  []
  (format "%s (%s): %s\n%s (%s): %s\nClojure: %s"
          (sys-prop "os.name")
          (sys-prop "os.arch")
          (sys-prop "os.version")
          (sys-prop "java.vm.name")
          (sys-prop "java.vendor")
          (sys-prop "java.version")
          (clojure-version)))

(defn in?
  "This function returns true if the provided seqenuce contains the given
  elment."
  [xs x]
  (some #(= x %) xs))

(defn sys-prop
  "Get a system property"
  [^String prop-key]
  (System/getProperty prop-key))
