(ns snailfinder-app.shared.scenes.snail-list
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.shared.config :as config]
            [snailfinder-app.shared.data :as data]
            [snailfinder-app.shared.ui :as ui]
            [snailfinder-app.ios.style :refer [style]]))


(defn- snail-list-item
  [[snail-key {:keys [image-white] :as snail}]]
  [ui/view
   [ui/text {:style    {:font-weight :bold}
             :on-press #(dispatch [:nav/push {:key       :routes/snail-details
                                              :title     "Snail Details"
                                              :snail-key snail-key}])}
    (:name snail)]
   [ui/view {:style {:height 100
                     :width  100}}
    [ui/image {:resizeMode :contain
               :style      {:position :absolute
                            :top      0
                            :bottom   0
                            :left     0
                            :right    0}
               :source     {:uri (str config/images-base-url "snails/" image-white)}}]]])


(defn scene-snail-list
  [props]
  [ui/view {:style {:flex 1}}
   (into [ui/scroll-view {:align-items "stretch"}]
     (mapv (fn [snail]
             [snail-list-item snail]) data/snails))])
