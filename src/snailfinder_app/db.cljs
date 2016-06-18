(ns snailfinder-app.db
  (:require [schema.core :as s :include-macros true]))

;; schema of app-db
(def schema {:greeting s/Str
             :current-page s/Any})

;; initial state of app-db
(def app-db {:greeting "Hello Clojure in iOS and Androiasddad sd!"
             :current-page [:home]})
