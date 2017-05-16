(ns reagent-comps.autoselect
  (:require [beicon.core :as rx]
            [components.beiconx :as rxt]
            [components.forms :as forms]
            [reagent.core :as r]))


(defn model [{:keys [suggest-fn async? on-select] :or {async? false}}]
  (let [hint        (rx/subject)
        selection   (rx/subject)
        _           (rx/on-value selection on-select)
        suggestions (if async?
                      (rx/flat-map suggest-fn hint)
                      (rx/map suggest-fn hint))
        dirty       (rx/merge
                     (rx/map (constantly true) hint)
                     (rx/map (constantly false) selection))
        suggest?    (rx/merge
                     (rx/map (constantly false) selection)
                     (rx/map seq suggestions))]
    {:hint        hint
     :selection   selection
     :suggestions suggestions
     :dirty       dirty
     :suggest?    suggest?}))


(defn- option-fn [render on-select]
  (fn [s]
    [:li.option-item {:on-click #(on-select s)}
     [render s]]))


(defn view [{:keys [suggestions dirty suggest? selection hint]}
            {:keys [show render class-name] :or {class-name ""}}]
  (let [buffer  (rxt/to-ratom "" (rx/merge hint (rx/map show selection)))
        options (rxt/to-ratom [] suggestions)
        show?   (rxt/to-ratom false suggest?)]
    (fn []
      [:div.autoselect
       [:input.input {:type       "text"
                      :class-name class-name
                      :on-change  (forms/input hint)
                      :value      @buffer}]
       (into [:ul.options {:class-name (if @show? "show" "")}]
             (map (option-fn render selection)) @options)])))
