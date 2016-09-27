(ns snailfinder-app.shared.scenes.snail-details
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.shared.config :as config]
            [snailfinder-app.shared.data :as data]
            [snailfinder-app.shared.ui :as ui]
            [snailfinder-app.ios.style :refer [style]]))


(defn- section
  [title text]
  [ui/view
   [ui/text {} " "] ;; TODO fix style
   [ui/text {:style {:font-weight :bold}}
    title]
   [ui/text {} text]])


(defn scene-snail-details
  [props]
  (let [idx        (aget props "scene" "index")
        snail-key  (keyword (aget props "scene" "route" "snail-key"))
        snail-data (js->clj (get data/snails snail-key) :keywordize-keys true)]
    [ui/view {:style {:flex 1}}
     [ui/scroll-view {:align-items "stretch"}
      [ui/text {:style {:font-weight :bold}}
       (:name snail-data)]
      [ui/view {:style {:height 150}}
       [ui/image {:resizeMode :contain
                 :style      {:position :absolute
                              :top      0
                              :bottom   0
                              :left     0
                              :right    0}
                 :source     {:uri (str config/images-base-url "snails/" (:image-white snail-data))}}]]
      [section "Common Name" (:common-name snail-data)]
      [section "Family" (:family snail-data)]
      [section "Ecology" (:ecology snail-data)]
      [section "GB Distribution" (:gb-distribution snail-data)]
      [section "World Distribution" (:world-distribution snail-data)]
      [section "Size" (:size snail-data)]
      [section "Conservation Status" (:conservation-status snail-data)]
      [section "Similar Species" (:similar-species snail-data)]
      #_(into [ui/view] (map
                        (fn [[k v]]
                          [ui/view {}
                           [ui/text {:style {:font-weight :bold}} k]
                           [ui/text v]])
                        (get data/snails snail-key)))]]))