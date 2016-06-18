(ns snailfinder-app.ios.detail-page
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :refer [blank?]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.handlers]
            [snailfinder-app.subs]
            [snailfinder-app.ios.ui :as ui]))


(def styles
  {:app       {:position "absolute"
               :top      0
               :left     0
               :bottom   0
               :right    0}
   :statusbar {:background-color "#01579B"
               :height           50}
   :toolbar   {:position         "relative"
               :background-color "#01579B"}
   :scenes    {:main  {:view      {:align-items "stretch"}
                       :city-card {:card        {:height     105
                                                 :flex       1
                                                 }
                                   :view        {:padding          10
                                                 :background-color "green"}
                                   :title       {:font-size 22
                                                 :color     "white"}
                                   :temp        {:font-size   22
                                                 :font-weight "bold"
                                                 :color       "white"}
                                   :description {:font-weight   "bold"
                                                 :margin-bottom 5
                                                 :color         "white"}
                                   :key         {:color "white"
                                                 :text-shadow-color "black"}
                                   :value       {:font-weight "bold"
                                                 :color       "white"}}}
               :about {:view   {:padding 16
                                :flex    1}
                       :title  {:font-weight   "bold"
                                :margin-bottom 4}
                       :author {:margin-top    4
                                :margin-bottom 20}}}})

(defn detail-page
  []
  (fn []
    (let [style (get-in styles [:scenes :main])]
      [ui/view {:style (get style :view)
             :flex  1}
       [ui/scroll
        [ui/text {:style {}} ""]
        [ui/text {:style {}} ""]
        [ui/text {:style {}} ""]
        [ui/text {:style {}} ""]
        [ui/text {:style {}} ""]
        [ui/text {:style {}} "detail-page"]

        ]])))