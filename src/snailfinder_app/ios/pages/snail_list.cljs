(ns snailfinder-app.ios.pages.snail-list
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :refer [blank?]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.config :as config]
            [snailfinder-app.handlers]
            [snailfinder-app.subs]
            [snailfinder-app.ios.ui :as ui]
            [snailfinder-app.ios.style :refer [colors font-size link]]))

(defn snail-row
  [[k {:keys [name common-name image-white] :as v}]]
  [ui/touchable
   {:on-press #(dispatch [:set-page [:detail-page k]])}
   [ui/view
    [ui/text {:style {:color      (:primary colors)
                      :fontWeight "bold"}} name]
    [ui/view {:style {:height 100
                      :width 100}}
     [ui/image {:resizeMode :contain
                :style {:position :absolute
                       :top 0
                       :bottom 0
                       :left 0
                       :right 0}
               :source {:uri (str config/images-base-url "snails/" image-white) #_(str config/images-base-url image-white)}
               }]]
    [ui/text {} common-name]]])

(defn list-page
  []
  (let [snails* (subscribe [:get-snails])]
    (fn []
      [ui/scroll
       [ui/view
        [ui/text {} ""]
        [ui/text {} ""]
        [ui/text {} ""]
        [ui/text {} ""]
        [ui/text {} ""]
        [ui/text {} ""]
        [ui/text {} ""]
        [ui/touchable {:on-press #(dispatch [:set-page [:home]])}
         [ui/view
          [ui/text {:style link} (str "< Home")]]]
        [ui/text {} ""]
        (into [ui/view]
          (mapv snail-row @snails*))]])))