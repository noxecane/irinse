(ns reagent-comps.beiconx-test
  (:require [beicon.core :as rx]
            [reagent-comps.beiconx :as sut]
            [cljs.test :as t :refer-macros [deftest async is testing]]))


(deftest test-factory
  (async done
    (let [[f v$] (sut/factory)
          sub    (rx/on-value v$ #(is (<= % 10)))]
     (doseq [x [1 2 4 59 10]]
       (f x))
     (rx/cancel! sub)
     (done))))


(deftest test-converter
  (testing "Simple values"
    (let [[f v$] (sut/factory)
          state  (sut/x->ratom "" v$)]
      (f "First")
      (f "Latest")
      (is (= "Latest" @state))))

  (testing "Using fold"
    (let [[f v$] (sut/factory)
          state  (sut/x->ratom [] conj v$)]
      (doseq [x (range 10)]
        (f x))
      (is (-> @state count (= 10))))))

