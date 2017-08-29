(defproject clj-specs "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha16"]
                 [org.clojure/test.check "0.10.0-alpha2"]
                 [org.clojure/tools.namespace "0.2.11"]]
  :dev-dependencies []
  :repl-options { :init-ns dev }
  :source-paths ["src" "dev"]
  :plugins [[lein-marginalia "0.9.0"]])
