(ns reagent-comps.beiconx
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [beicon.core :as rx]
            [reagent.core :as r]
            [cljs.core.async :as a]))


(defn factory
  "Create a triple of an observable, its push function
   and a termination function."
  []
  (let [sub      (rx/subject)]
    [sub #(rx/push! sub %) #(rx/end! sub)]))


(defn x->ratom
  "Convert an observable to a reagent atom. Can accept a fold to
   reduce over the state rather than write directly to it."
  ([stream]
   (x->ratom nil stream))

  ([initial-value stream]
   (let [state (r/atom initial-value)
         sub   (rx/subscribe stream #(reset! state %))]
     state))

  ([initial-value fold stream]
   (let [state (r/atom initial-value)
         sub   (rx/subscribe stream #(swap! state fold %))]
     state)))


(defn from-chan
  "Creates an observable consuming events from the given core.async
   channel."
  [ch]
  (let [[v f c] (factory)]
    (go-loop [x (a/<! ch)]
      (if (some? x) (f x) (c)))
    v))
