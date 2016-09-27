(ns snailfinder-app.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.shared.assets :as a]
            [snailfinder-app.shared.data :as data]
            [snailfinder-app.shared.handlers]
            [snailfinder-app.shared.scenes.snail-details :refer [scene-snail-details]]
            [snailfinder-app.shared.scenes.snail-list :refer [scene-snail-list]]
            [snailfinder-app.ios.style :refer [style]]
            [snailfinder-app.shared.subs]
            [snailfinder-app.shared.ui :as ui]))


(defn- nav-title [props]
  ;(.log js/console "props" props)
  [ui/header-title (aget props "scene" "route" "title")])


(defn- header
  [props]
  [ui/navigation-header
   (assoc
     (js->clj props)
     :render-title-component #(r/as-element (nav-title %))
     :on-navigate-back #(dispatch [:nav/pop nil]))])


(defn- scene-home [props]
  (let [idx        (aget props "scene" "index")
        idx        (aget props "scene" "index")
        next-title (str "Route " (inc idx))
        next-key   (keyword (str idx))]
    [ui/view {:style (:view style)}
     [ui/image {:source a/logo-img
             :style  (:image style)}]
     [ui/touchable-highlight
      {:style    (:button style)
       :on-press #(dispatch [:nav/push {:key   :routes/snail-list
                                        :title "Snail List"}])}
      [ui/text {:style (:button-text style)} "Snails"]]]))


(defn- app-root []
  (let [nav (subscribe [:nav/state])]
    (fn []
      [ui/card-stack {:on-navigate-back #(dispatch [:nav/pop nil])
                   :render-header    #(r/as-element (header %))
                   :navigation-state @nav
                   :style            {:flex 1}
                   :render-scene     #(r/as-element
                                       (do
                                         (case (keyword (aget % "scene" "route" "key"))
                                           :snail-list (scene-snail-list %)
                                           :snail-details (scene-snail-details %)
                                           (scene-home %))))}])))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent ui/app-registry "NavigatorCljs" #(r/reactify-component app-root)))