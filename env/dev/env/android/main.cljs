(ns ^:figwheel-no-load env.android.main
  (:require [om.next :as om]
            [lalala.android.core :as core]
            [lalala.state :as state]
            [figwheel.client :as figwheel :include-macros true]))

(enable-console-print!)

(figwheel/watch-and-reload
  :websocket-url "ws://10.0.3.2:3449/figwheel-ws"
  :heads-up-display false
  :jsload-callback #(om/add-root! state/reconciler core/AppRoot 1))

(core/init)

(def root-el (core/app-root))