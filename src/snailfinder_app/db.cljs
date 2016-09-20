(ns snailfinder-app.db)

;; initial state of app-db
(def app-db {:nav {:index    0
                   :key      :home
                   :routes [{:key :first-route
                             :title "First route"}]}})