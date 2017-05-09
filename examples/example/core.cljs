(ns example.core
  (:require [reagent.core :as r]
            [reagent-comps.forms.autoselect :as autoselect]))


(def languages
  [["Clojure" :hard]
   ["Java" :complex]
   ["Javascript" :messy]
   ["PHP" :ugly]
   ["Python" :popular]
   ["Haskell" :esoteric]
   ["Bash" :dirty]
   ["C" :practical]
   ["AWK" :proper]])


(defn find-lang [name]
  (letfn [(lang? [[lang-name :as l]]
            (let [n (clojure.string/lower-case name)
                  ln (clojure.string/lower-case lang-name)]
              (clojure.string/starts-with? ln n)))]
    (filter lang? languages)))


(defn render [[name type]]
  [:div.lang
   [:p.name name]
   [:small (str type)]])


(defn lang-auto []
  [autoselect/view
   (autoselect/model {:suggest-fn find-lang
                      :on-select  #(println "Selected" %)})
   {:show   first
    :render render}])


(r/render-component
 [:div
  [lang-auto]]
 (. js/document (getElementById "app")))
