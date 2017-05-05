(ns reagent-comps.beiconx
  (:require [beicon.core :as rx]))


(defn factory []
  (let [stream (atom nil)
        stream$ (rx/from-atom stream)
        next-val (fn [& args] (reset! stream (vec args)))]
    [next-val stream$]))
