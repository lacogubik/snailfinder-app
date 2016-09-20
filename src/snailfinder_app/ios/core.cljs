(ns snailfinder-app.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.shared.handlers]
            [snailfinder-app.shared.subs]))

(def react-native (js/require "react-native"))

(def app-registry (.-AppRegistry react-native))
(def text (r/adapt-react-class (.-Text react-native)))
(def view (r/adapt-react-class (.-View react-native)))
(def image (r/adapt-react-class (.-Image react-native)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight react-native)))
(def card-stack (r/adapt-react-class (.-CardStack (.-NavigationExperimental react-native))))
(def navigation-header-comp (.-Header (.-NavigationExperimental react-native)))
(def navigation-header (r/adapt-react-class navigation-header-comp))
(def header-title (r/adapt-react-class (.-Title (.-Header (.-NavigationExperimental react-native)))))

(def logo-img (js/require "./images/cljs.png"))

(def style
  {:view        {:flex-direction "column"
                 :margin         40
                 :margin-top     (.-HEIGHT navigation-header-comp)
                 :align-items    "center"}
   :title       {:font-size     30
                 :font-weight   "100"
                 :margin-bottom 20
                 :text-align    "center"}
   :button-text {:color       "white"
                 :text-align  "center"
                 :font-weight "bold"}
   :image       {:width         80
                 :height        80
                 :margin-bottom 30}
   :button      {:background-color "#999"
                 :padding          10
                 :margin-bottom    10
                 :border-radius    5}})

(defn nav-title [props]
  ;(.log js/console "props" props)
  [header-title (aget props "scene" "route" "title")])

(defn header
  [props]
  [navigation-header
   (assoc
     (js->clj props)
     :render-title-component #(r/as-element (nav-title %))
     :on-navigate-back #(dispatch [:nav/pop nil]))])

(defn scene [props]
  (let [idx        (aget props "scene" "index")
        next-title (str "Route " (inc idx))
        next-key   (keyword (str idx))]
    [view {:style (:view style)}
     [text {:style (:title style)} (str "Hello #" idx)]
     [image {:source logo-img
             :style  (:image style)}]
     #_[touchable-highlight
      {:style    (:button style)
       :on-press #(dispatch [:nav/push {:key   next-key
                                        :title next-title}])}
      [text {:style (:button-text style)} "Next route"]]
     #_[touchable-highlight
      {:style    (:button style)
       :on-press #(dispatch [:nav/home nil])}
      [text {:style (:button-text style)} "Go home"]]
     [touchable-highlight
      {:style    (:button style)
       :on-press #(dispatch [:nav/push {:key   :routes/snail-list
                                        :title "Snail list"}])}
      [text {:style (:button-text style)} "Snail list"]]
     [touchable-highlight
      {:style    (:button style)
       :on-press #(dispatch [:nav/push {:key   :routes/snail-details
                                        :title "Snail details"}])}
      [text {:style (:button-text style)} "Snail details"]]]))

(defn scene-snail-list [props]
  [view {:style (:view style)}
   [text {:style (:title style)} "Snail List"]
   [touchable-highlight
    {:style    (:button style)
     :on-press #(dispatch [:nav/home nil])}
    [text {:style (:button-text style)} "Go home"]]])

(defn scene-snail-details [props]
  [view {:style (:view style)}
   [text {:style (:title style)} "Snail details"]
   [touchable-highlight
    {:style    (:button style)
     :on-press #(dispatch [:nav/home nil])}
    [text {:style (:button-text style)} "Go home"]]])

(defn app-root []
  (let [nav (subscribe [:nav/state])]
    (fn []
      [card-stack {:on-navigate-back #(dispatch [:nav/pop nil])
                   :render-header    #(r/as-element (header %))
                   :navigation-state @nav
                   :style            {:flex 1}
                   :render-scene     #(r/as-element
                                       (do
                                         (case (keyword (aget % "scene" "route" "key"))
                                           :snail-list (scene-snail-list %)
                                           :snail-details (scene-snail-details %)
                                           (scene %))))}])))

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "NavigatorCljs" #(r/reactify-component app-root)))