(ns reagent-comps.beiconx-test
  (:require [beicon.core :as rx]
            [reagent-comps.beiconx :as sut]
            [cljs.test :as t :refer-macros [deftest async is]]))


(deftest test-factory
  (async done
   (let [[f v$] (sut/factory)]
     (rx/subscribe v$ (fn [args]
                        (let [v (apply + args)]
                          (is 10 v))))
     (f 10)
     (f 1 9)
     (f 5 5)
     (f 2 3 4 1)
     (done))))
