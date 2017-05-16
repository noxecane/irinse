(defproject tsaron/components "0.1.0"
  :description "A collection of components built uing reagent, beicon and bulma"
  :url "tsaron.github.io/components"
  :license {:name "General Public License v3.0"
            :url  "https://www.gnu.org/licenses/gpl-3.0.en.html"}
  :min-lein-version "2.7.1"
  :dependencies [[org.clojure/clojure       "1.8.0"]
                 [org.clojure/clojurescript "1.9.494"]
                 [org.clojure/core.async    "0.3.441"
                  :exclusions [org.clojure/tools.reader]]

                 [funcool/beicon "3.2.0"]
                 [reagent "0.6.1"]]

  :plugins [[lein-cljsbuild "1.1.5"
             :exclusions [[org.clojure/clojure]]]]

  :profiles {:dev {:plugins      [[lein-figwheel "0.5.10"]]
                   :dependencies [[binaryage/devtools      "0.9.0"]
                                  [figwheel-sidecar        "0.5.9"]
                                  [com.cemerick/piggieback "0.2.1"]]

                   :source-paths ["src" "demo" "dev"]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}

             :prod {:source-paths ["src"]}}

  :cljsbuild {:builds [{:id "dev"
                        :figwheel true
                        :source-paths ["src" "demo"]
                        :compiler {:main                 "demo.core"
                                   :asset-path           "out"
                                   :output-to            "demo.js"
                                   :output-dir           "out/"
                                   :preloads             [devtools.preload]
                                   :optimizations        :none
                                   :source-map-timestamp true}}]}

  :clean-targets ^{:protect false} [:target-path "out"]
  )
