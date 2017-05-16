(ns irinse.modal
  (:require [beicon.core :as rx]
            [irinse.beiconx :as rxt]))


(defn view
  [{:keys [trigger]} & children]
  (let [open? (rxt/to-ratom false trigger)]
    (fn []
      [:div.modal (if @open? "is-active" "")
       [:div.modal-background {:on-click #(trigger false)}]
       (into [:div.modal-content] children)
       [:button.modal-close {:on-click #(trigger false)}]])))


(defn card-footer [& children]
  (into [:footer.modal-card-foot] children))


(defn view-card
  [{:keys [trigger]}
   {:keys [title footer]}
   & children]
  (let [open? (rxt/to-ratom false trigger)]
    [:div.modal
     [:div.modal-background {:on-click #(trigger false)}]
     [:div.modal-card
      [:header.modal-card-head
       [:p.modal-card-title title]
       [:button.delete {:on-click #(trigger false)}]]
      (into [:div.modal-card-body] children)
      footer]]))
