(ns env.ios.main
  (:require [lalala.ios.core :as core]
            [lalala.client.test-runner :as test-runner]))

(core/init)
(test-runner/run-tests-remote-receiver)
