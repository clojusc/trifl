(defproject clojusc/trifl "0.3.0-SNAPSHOT"
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
    :test {
      :dependencies [
        [clj-http "3.7.0"]
        [com.amazonaws/aws-java-sdk-s3 "1.11.28"]
        [slingshot "0.12.2"]]
      :plugins [
        [jonase/eastwood "0.2.5"]
        [lein-ancient "0.6.15" :exclusions [clj-http com.amazonaws/aws-java-sdk-s3]]
        [lein-kibit "0.1.6"]]}
    :dev {
      :source-paths ["dev-resources/src"]
      :repl-options {
        :init-ns trifl.dev}
      :dependencies [
        [org.clojure/tools.namespace "0.2.11"]]}}
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
