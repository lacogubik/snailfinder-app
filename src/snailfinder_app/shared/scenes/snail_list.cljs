(ns snailfinder-app.shared.scenes.snail-list
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.shared.assets :as a]
            [snailfinder-app.shared.data :as data]
            [snailfinder-app.shared.ui :as ui]
            [snailfinder-app.ios.style :refer [style]]))


(defn- snail-list-item
  [[snail-key snail]]
  [ui/text {:style    {}
            :on-press #(dispatch [:nav/push {:key       :routes/snail-details
                                             :title     "Snail Details"
                                             :snail-key snail-key}])}
   (:name snail)])


(defn scene-snail-list
  [props]
  [ui/view {:style {:flex 1}}
   (into [ui/scroll-view {:align-items "stretch"}]
     (mapv (fn [snail]
             [snail-list-item snail]) data/snails))])
