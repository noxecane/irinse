(ns reagent-comps.beiconx
  (:require [beicon.core :as rx]
            [reagent.core :as r]))


(defn factory []
  (let [stream (atom nil)
        stream$ (rx/from-atom stream)
        next-val #(reset! stream %)]
    [next-val stream$]))


(defn x->ratom
  ([initial-value stream]
   (let [state (r/atom initial-value)
         sub   (rx/subscribe stream #(reset! state %))]
     state))

  ([initial-value fold stream]
   (let [state (r/atom initial-value)
         sub   (rx/subscribe stream #(swap! state fold %))]
     state)))
