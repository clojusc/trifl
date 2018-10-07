(defproject clojusc/trifl "0.4.0"
  :description "The Clojure utility library that dares not speaks its name"
  :url "https://github.com/clojusc/trifl"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :exclusions [org.clojure/clojure]
  :dependencies [
    [org.clojure/clojure "1.9.0"]]
  :profiles {
    :ubercompile {
      :aot :all}
    :lint {
      :source-paths ^:replace ["src"]
      :test-paths ^:replace []
      :plugins [
        [jonase/eastwood "0.3.1"]
        [lein-ancient "0.6.15"]
        [lein-bikeshed "0.5.1"]
        [lein-kibit "0.1.6"]
        [lein-shell "0.5.0"]
        [venantius/yagni "0.1.6"]]}
    :test {
      :aot :all
      :plugins [
        [lein-ltest "0.3.0"]]
      :test-selectors {
        :default :unit
        :unit :unit
        :system :system
        :integration :integration}}
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns trifl.dev}
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]
        [org.clojure/data.xml "0.0.8"]]}}
  :aliases {
    "repl" ["do"
      ["clean"]
      ["repl"]]
    "ubercompile" ["with-profile" "+ubercompile" "compile"]
    ;; Linting and tests
    "check-vers" ["with-profile" "+lint" "ancient" "check" ":all"]
    "check-jars" ["with-profile" "+lint" "do"
      ["deps" ":tree"]
      ["deps" ":plugin-tree"]]
    "check-deps" ["do"
      ["check-jars"]
      ["check-vers"]]
    "kibit" ["with-profile" "+lint" "do"
      ["shell" "echo" "== Kibit =="]
      ["kibit"]]
    "eastwood" ["with-profile" "+lint" "eastwood" "{:namespaces [:source-paths]}"]
    "lint" ["do"
      ["kibit"]
      ["eastwood"]]
    "ltest" ["with-profile" "+test" "ltest"]
    ;; Build
    "build" ^{:doc "Perform build steps."} ["do"
      ["clean"]
      ["ubercompile"]
      ["check-vers"]
      ["lint"]
      ["ltest" ":all"]
      ["uberjar"]]})
