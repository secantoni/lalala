(ns lalala.ios.core
  (:require [om.next :as om :refer-macros [defui]]
            [re-natal.support :as sup]
            [lalala.state :as state]))

(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))

(defn create-element [rn-comp opts & children]
  (apply js/React.createElement rn-comp (clj->js opts) children))

(def app-registry (.-AppRegistry ReactNative))
(def view (partial create-element (.-View ReactNative)))
(def text (partial create-element (.-Text ReactNative)))
(def image (partial create-element (.-Image ReactNative)))
(def touchable-highlight (partial create-element (.-TouchableHighlight ReactNative)))
(def scroll-view (partial create-element (.-ScrollView ReactNative)))
(def list-view (partial create-element (.-ListView ReactNative)))
(def DataSource (.-DataSource (.-ListView ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defui AppRoot
  Object
  (render [this]
    (view
      {:style {:flex 1 :justifyContent "center" :alignItems "center"}}
      (text
        {:style {:fontSize 30}}
        "Lalala!"))))

(defonce RootNode (sup/root-node! 1))
(defonce app-root (om/factory RootNode))

(defn init []
  (om/add-root! state/reconciler AppRoot 1)
  (.registerComponent app-registry "Lalala" (fn [] app-root)))
