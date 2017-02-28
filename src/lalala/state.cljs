(ns lalala.state
  (:require [om.next :as om]
            [re-natal.support :as sup]))

(defonce app-state (atom {:app/msg "Hello Clojure in iOS and Android!"
                          :cards   [{:id (.toString (random-uuid))}]}))

;(def items (doall (repeatedly 100000 #(str "foo" (rand-int 1000000)))))
(def items "foo")

(defmulti read om/dispatch)
(defmethod read :default
  [{:keys [state]} k _]
  (let [st @state]
    (if-let [[_ v] (find st k)]
      {:value v}
      {:value :not-found})))

(defmethod read :items
  [{:keys [state]} k _]
  {:value items})

(defmulti mutate om/dispatch)
(defmethod mutate 'add-card
  [{:keys [state query target ast] :as env} key params]
  {:action #(swap! state (fn [state]
                           ; TODO: why should :ui/routes be a vector?
                           (let [current (:cards state)]
                             (assoc state :cards (conj current {:id (.toString (random-uuid))})))))})

(defonce reconciler
         (om/reconciler
           {:state        app-state
            :parser       (om/parser {:read read :mutate mutate})
            :root-render  sup/root-render
            :root-unmount sup/root-unmount}))
