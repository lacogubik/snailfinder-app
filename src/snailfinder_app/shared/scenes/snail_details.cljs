(ns snailfinder-app.shared.scenes.snail-details
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.shared.assets :as a]
            [snailfinder-app.shared.data :as data]
            [snailfinder-app.shared.ui :as ui]
            [snailfinder-app.ios.style :refer [style]]))


(defn scene-snail-details
  [props]
  (let [idx       (aget props "scene" "index")
        snail-key (keyword (aget props "scene" "route" "snail-key"))
        snail-data (js->clj (get data/snails snail-key) :keywordize-keys true)]
    [ui/view {:style {:flex 1}}
     [ui/scroll-view {:align-items "stretch"}
      [ui/text {:style (:title style)} (:name snail-data)]
      (into [ui/view] (map
                        (fn [[k v]]
                          [ui/view {}
                           [ui/text {:style {:font-weight :bold}} k]
                           [ui/text v]])
                        (get data/snails snail-key)))]]))