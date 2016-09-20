(ns snailfinder-app.events
  (:require [re-frame.core :refer [reg-event-db after]]
    [snailfinder-app.db :as db :refer [app-db]]))


(reg-event-db
  :initialize-db
  (fn [_ _]
    app-db))


(reg-event-db
  :set-greeting
  (fn [db [_ value]]
    (assoc db :greeting value)))
