(ns lalala.client.client-tests
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop :include-macros true]
            [clojure.test.check.clojure-test :refer-macros [defspec]]
            [cljs.test :as test]))

(def prop1
  (prop/for-all
    [data gen/int]
    (int? data)))
(defspec prop1-test 1000 prop1)

(def prop2
  (prop/for-all
    [data gen/int]
    (int? data)))
(defspec prop2-test 1000 prop2)
