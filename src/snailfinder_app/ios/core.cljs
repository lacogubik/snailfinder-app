(ns snailfinder-app.ios.core
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :refer [blank?]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.handlers]
            [snailfinder-app.subs]
            [snailfinder-app.ios.pages.snail-details :refer [detail-page]]
            [snailfinder-app.ios.pages.snail-list :refer [list-page]]))


(set! js/window.React (js/require "react"))
(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def navigator (r/adapt-react-class (.-NavigatorIOS ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def logo-img (js/require "./images/cljs.png"))

(defn alert [title]
  (.alert (.-Alert ReactNative) title))

(defn show-dialog [{text     :text
                    callback :callback}]
  (.prompt (.-AlertIOS ReactNative) text nil callback))

(def scroll
  (r/adapt-react-class (.-ScrollView ReactNative)))

(def touchable
  (r/adapt-react-class (.-TouchableWithoutFeedback ReactNative)))


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

(defn menu-item-component [{:keys [item style]}]
  (fn []
    (let []
      [touchable {:on-press #(dispatch [:set-page [(:id item)]])}
       [view
          {:style (get-in styles [:scenes :main :city-card :card])}
        [view {:style (get-in styles [:scenes :main :city-card :view])}
        [view
         [view {:flex-direction  "row"
                :justify-content "space-between"}
          [text {:style (get style :title)} (:slug item)]
          ]
         [view {:flex-direction "row"}
          [text {:style (get-in style [:description])}
           (:title item)]]]]]
       ;[image {:style  (get-in styles [:scenes :main :city-card :card])
       ;        :source {:uri media-url}}
       ; ]
       ])))

(def menu-items
  [{:slug "Snail Indetifier"
    :icon ""
    :id :home
    :title "Identify which snail you've just found"}
   {:slug "Snail Details"
    :icon ""
    :id :detail-page
    :title "Just one"}
   {:slug "Snail List"
    :icon ""
    :id :list-page
    :title "All of the snails, listed A-Z"}])


(defn home-view
  []
  (let [style (get-in styles [:scenes :main])]
    (fn []
      [view {:style (get style :view)
             :flex  1}

       [scroll
        [text {:style {}} "Home"]
        [menu-item-component {:item  (first menu-items)
                              :style (get style :city-card)}]
        [menu-item-component {:item  (second menu-items)
                              :style (get style :city-card)}]
        [menu-item-component {:item  (nth menu-items 2)
                              :style (get style :city-card)}]]])))




(defn root-scene [{navigator :navigator}]
  (let [current-page (subscribe [:get-current-page])]
    (fn []
      (let []
        [view {:flex 1}
         (case (first @current-page)
           :home [home-view ]
           :detail-page [detail-page]
           :list-page [list-page])]))))

(defn app-root []
  [navigator
   {:initial-route {:title                 "Snailfinder"
                    :component             (r/reactify-component root-scene)
                    }
    :style         {:position "absolute"
                    :top      0
                    :left     0
                    :bottom   0
                    :right    0}}])

(defn init []
  (dispatch-sync [:initialize-db])
  (.registerComponent app-registry "Snailfinder" #(r/reactify-component app-root)))
