(ns snailfinder-app.handlers
  (:require [re-frame.core :refer [reg-event-db after]]))


(defn dec-to-zero
  "Same as dec if not zero"
  [arg]
  (if (< 0 arg)
    (dec arg)
    arg))


(reg-event-db
  :nav/push
  (fn [db [_ value]]
    (-> db
      (update-in [:nav :index] inc)
      (update-in [:nav :routes] #(conj % value)))))


(reg-event-db
  :nav/pop
  (fn [db [_ _]]
    (-> db
      (update-in [:nav :index] dec-to-zero)
      (update-in [:nav :routes] pop))))


(reg-event-db
  :nav/home
  (fn [db [_ _]]
    (-> db
      (assoc-in [:nav :index] 0)
      (assoc-in [:nav :routes] (vector (get-in db [:nav :routes 0]))))))
