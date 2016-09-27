(ns snailfinder-app.ios.style
  (:require [snailfinder-app.shared.ui :as ui]))


(def style
  {:view        {:flex-direction "column"
                 :margin         40
                 :margin-top     (.-HEIGHT ui/navigation-header-comp)
                 :align-items    "center"}
   :title       {:font-size     30
                 :font-weight   "100"
                 :margin-bottom 20
                 :text-align    "center"}
   :button-text {:color       "white"
                 :text-align  "center"
                 :font-weight "bold"}
   :image       {:width         106
                 :height        100
                 :margin-bottom 30}
   :button      {:background-color "#999"
                 :padding          10
                 :margin-bottom    10
                 :border-radius    5}})
