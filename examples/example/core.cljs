(ns example.core
  (:require [reagent.core :as r]
            [reagent-comps.forms.autoselect :as autoselect]))


(r/render-component
 [:div]
 (. js/document (getElementById "app")))
