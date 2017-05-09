(ns reagent-comps.forms.core)


(defn- text-event [ev]
  (-> ev .-target .-value))


(defn input
  "Returns a function that's the composition of value extraction
   and the given function. It also supports adding fixed args that
   will be passed as an array with the event's value as the last
   element to the passed function."
  ([ob]
   (comp ob text-event))

  ([ob & args]
   (let [arg-ls (vec args)]
     #(->> (text-event %)
           (conj arg-ls)
           ob))))
