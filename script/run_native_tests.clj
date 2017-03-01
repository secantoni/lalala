
(require 'lalala.client.tests-receiver)
(require 'clojure.java.shell)
(require '[clojure.core.async :as a])

(println "Starting Tests Receiver...")
(lalala.client.tests-receiver/run)
(println "Tests Receiver Running...")

(a/go
  (clojure.java.shell/sh "./node_modules/.bin/react-native" "start"))

(a/<!! (a/timeout 3000))

(println "Starting Tests on Simulator...")
(let [r (clojure.java.shell/sh "./node_modules/.bin/react-native" "run-ios")]
  (if (not= 0 (:exit r))
    (println (:err r))
    (println "Tests on Simulator Running...")))
