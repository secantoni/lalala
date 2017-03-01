(ns lalala.client.test-runner
  (:require-macros [cljs.core.async.macros :as a])
  (:require [cljs.core.async :as a]
            [chord.client :as chord]
            [cljs.test :as test]
            [lalala.client.client-tests]))

(defn run-tests []
  (test/run-tests 'lalala.client.client-tests))

(defn run-tests-remote-receiver []
  (a/go (let [ws (a/<! (chord/ws-ch "ws://127.0.0.1:3000/ws"))]
          (if-let [error (:error ws)]
            (throw (str "WS Error: " error))
            ; TODO: this should unblock the thread while running tests to complete sending messages as it goes
            (do
              (set-print-fn! (fn [& args]
                               (a/put! (:ws-channel ws) {:type :print :strs args})))
              (defmethod cljs.test/report [:cljs.test/default :end-run-tests] [m]
                (a/put! (:ws-channel ws) m))
              (let [s (system-time)
                    _ (run-tests)
                    e (system-time)
                    dt (- e s)]
                (println (str "Tests ran in " dt " ms"))))))))
