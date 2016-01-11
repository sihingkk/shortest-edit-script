(defproject shortest-edit-script "1.0.0"
  :description "Shortest edit script"
  :url "http://sihingkk.github.io"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                [criterium "0.4.3"]
                [org.clojure/test.check "0.9.0"]]
  :main ^:skip-aot shortest-edit-script.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
