(defproject clojusc/trifl "0.2.0"
  :description "The Clojure utility library that dares not speaks its name"
  :url "https://github.com/clojusc/trifl"
  :license {
    :name "Apache License, Version 2.0"
    :url "http://www.apache.org/licenses/LICENSE-2.0"}
  :excludsions [org.clojure/clojure]
  :dependencies [
    [org.clojure/clojure "1.8.0"]]
  :profiles {
    :ubercompile {
      :aot :all}
    :test {
      :exclusions [org.clojure/clojure]
      :plugins [
        [jonase/eastwood "0.2.4"]
        [lein-kibit "0.1.5"]
        [lein-ancient "0.6.12"]]}
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns trifl.dev}
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"
         :exclusions [org.clojure/clojure]]]}}
  :aliases {
    "check-deps" [
      "with-profile" "+test" "ancient" "check" ":all"]
    "kibit" [
      "with-profile" "+test" "do"
        ["shell" "echo" "== Kibit =="]
        ["kibit"]]
    "outlaw" [
      "with-profile" "+test"
      "eastwood" "{:namespaces [:source-paths] :source-paths [\"src\"]}"]
    "lint" [
      "with-profile" "+test" "do"
        ["check"] ["kibit"] ["outlaw"]]
    "build" ["with-profile" "+test" "do"
      ["check-deps"]
      ;["lint"]
      ["test"]
      ["compile"]
      ["with-profile" "+ubercompile" "compile"]
      ["clean"]
      ["uberjar"]]})
