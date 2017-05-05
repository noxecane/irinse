(ns reagent-comps.beiconx
  (:require-macros [cljs.core.async.macros :refer [go go-loop]])
  (:require [beicon.core :as rx]
            [reagent.core :as r]
            [cljs.core.async :as a]))


(defn factory
  "Create a triple of an observable, its push function
   and a termination function."
  []
  (let [sub (rx/subject)]
    [sub #(rx/push! sub %) #(rx/end! sub)]))


(defn x->ratom
  "Convert an observable to a reagent atom. Can accept a fold to
   reduce over the state rather than write directly to it."
  ([ob]
   (x->ratom nil ob))

  ([a ob]
   (let [state (r/atom a)
         sub   (rx/subscribe ob #(reset! state %))]
     state))

  ([a fold ob]
   (let [state (r/atom a)
         sub   (rx/subscribe ob #(swap! state fold %))]
     state)))


(defn from-chan
  "Creates an observable consuming events from the given core.async
   channel."
  [ch]
  (let [[v f c] (factory)]
    (go-loop [x (a/<! ch)]
      (when (some? x)
        (f x)
        (recur (a/<! ch)))
      (c))
    v))


(defn chan-map
  "flat-map for core.async channels"
  [f ob]
  (->> (rx/map f ob)
       (rx/map from-chan)
       (rx/flat-map)))
