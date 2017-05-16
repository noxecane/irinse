(ns demo.core
  (:require [reagent.core :as r]))


(r/render-component
 [:h1 "Hello world"]
 (. js/document (getElementById "app")))
