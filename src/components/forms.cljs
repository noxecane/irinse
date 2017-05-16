(ns components.forms
  (:require [beicon.core :as rx]
            [components.beiconx :as rxt]))


(defn- text-event [ev]
  (-> ev .-target .-value))


(defn input
  "Returns a function that's the composition of value extraction
   and the given function. It also supports setting the keys incase
   this is a write event.(Note the keys must be an array)"
  ([ob]
   (comp ob text-event))

  ([ob keys]
   #(->> (text-event %) (conj [keys]) ob)))


(defn writes
  "Reduces all write events over a ratom storing a map. Basically use this
   as the singular consumer of write events"
  [write]
  (letfn [massoc ([state [ks v]]
                  (assoc-in state ks v))]
    (rxt/to-ratom {} massoc write)))
