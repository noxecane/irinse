(ns irinse.beiconx
  (:require-macros [cljs.core.async.macros :refer [go-loop]])
  (:require [beicon.core :as rx]
            [reagent.core :as r]
            [cljs.core.async :as a]))


(extend-type rx/Observable
  IFn
  (-invoke
    ([this]
     (rx/end! this))
    ([this v]
     (rx/push! this v))))


(defn to-ratom
  "Convert an observable to a reagent atom. Can accept a fold to
   reduce over the state rather than write directly to it."
  ([ob]
   (to-ratom nil ob))

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
  (let [v (rx/subject)]
    (go-loop [x (a/<! ch)]
      (when (some? x)
        (v x)
        (recur (a/<! ch)))
      (v))
    v))


(defn log
  "Creates a subscriber that logs to console."
  [prefix ob]
  (rx/on-value ob #(println (str prefix ":") %)))
