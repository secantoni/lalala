(ns lalala.client.tests-receiver
  (:require [chord.http-kit :as chord-http-kit]
            [org.httpkit.server :refer [run-server]]
            [clojure.core.async :as a]
            [clojure.test :as test]))

(defn receive-message [m]
  (let [type (:type m)]
    (condp = type
      :print (apply println (:strs m))
      :end-run-tests (System/exit (if (test/successful? m) 0 1))
      (throw (Exception. (str "Not being able to handle message type: " type))))))

(defn handler [{:keys [ws-channel] :as req}]
  (a/go-loop []
    (let [{:keys [error message]} (a/<! ws-channel)]
      (if error
        (throw (Exception. (str error)))
        (do (receive-message message)
            (recur))))))

(defn run []
  (run-server (-> #'handler
                  chord-http-kit/wrap-websocket-handler)
              {:port 23423}))
