(ns snailfinder-app.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :refer [register-sub]]))

(register-sub
  :get-greeting
  (fn [db _]
    (reaction
      (get @db :greeting))))


(register-sub
  :get-current-page
  (fn [db _]
    (reaction
      (get @db :current-page))))