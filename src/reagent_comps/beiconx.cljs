(ns reagent-comps.beiconx
  (:require [beicon.core :as rx]))


(defn factory []
  (let [stream (atom nil)
        stream$ (rx/from-atom stream)
        next-val #(reset! stream %)]
    [next-val stream$]))
