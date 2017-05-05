(ns reagent-comps.beiconx-test
  (:require [beicon.core :as rx]
            [reagent-comps.beiconx :as sut]
            [cljs.test :as t :refer-macros [deftest async is]]))


(deftest test-factory
  (async done
   (let [[f v$] (sut/factory)]
     (rx/on-value v$ #(is (<= % 10)))
     (f 10)
     (f 9)
     (f 5)
     (f 1)
     (done))))
