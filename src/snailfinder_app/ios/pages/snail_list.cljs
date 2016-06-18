(ns snailfinder-app.ios.pages.snail-list
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :refer [blank?]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.handlers]
            [snailfinder-app.subs]
            [snailfinder-app.ios.ui :as ui]
            [snailfinder-app.ios.style :refer [colors font-size link]]))

(defn snail-row
  [[k {:keys [name common-name] :as v}]]
  [ui/touchable
   {:on-press #(dispatch [:set-page [:detail-page k]])}
   [ui/view
    [ui/text {:style {:color      (:primary colors)
                      :fontWeight "bold"}} name]
    [ui/image {:source (js/require "./images/cljs.png")}]
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