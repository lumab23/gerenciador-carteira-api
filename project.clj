(defproject gerenciador-carteira "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.7.0"]
                 [ring/ring-core "1.12.0"]
                 [ring/ring-defaults "0.5.0"]

                 [ring/ring-json "0.5.1"] ; para middleware JSON
                 [clj-http "3.12.3"] ; cliente HTTP (API externa)
                 [cheshire "5.13.0"] ; parser JSON
                 [ring/ring-jetty-adapter "1.12.0"]
                 ]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler gerenciador-carteira.handler/app}
  :main ^:skip-aot gerenciador-carteira.core ; core.clj como entrada
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
