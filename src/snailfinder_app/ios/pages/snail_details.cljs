(ns snailfinder-app.ios.pages.snail-details
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :refer [blank?]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.handlers]
            [snailfinder-app.subs]
            [snailfinder-app.ios.ui :as ui]
            [snailfinder-app.ios.style :refer [colors font-size link]]))


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

(defn text-row
  [k v]
  [ui/view
   [ui/text {:style {:paddingBottom 5
                     :fontWeight    "bold"
                     :color         (:primary colors)}}
    (str (name k))]
   [ui/text {:style {:paddingBottom 20}}
    (str v)]])

(defn detail-page
  [snail-id] ;;snail-id
  (let [snail (subscribe [:get-snail snail-id])]
    (print "[snail-id] snail key: " snail-id)
    (fn []
      (let [snail      @snail
            snail-rows (dissoc snail :image :family :common-name :name)]
        [ui/view {:style {:flex              1
                          :paddingHorizontal 15}}
         [ui/scroll
          [ui/text {:style {}} ""]
          [ui/text {:style {}} ""]
          [ui/text {:style {}} ""]
          [ui/text {:style {}} ""]
          [ui/text {:style {}} ""]
          [ui/touchable {:on-press #(dispatch [:set-page [:list-page]])}
           [ui/view
            [ui/text {:style link} (str "< Snail list")]]]
          [ui/view {:style {:paddingVertical 10}}
           [ui/text {:style link} (str "< " (:family snail) " family")]]
          [ui/view {:style {:paddingVertical 5}}
           [ui/text {:style {:fontSize (:h2 font-size)}}
            (:common-name snail)]]
          [ui/view {:style {:paddingVertical 5
                            :paddingBottom   30}} [ui/text {:style {:fontSize (:h1 font-size)}}
                                                   (:name snail)]]
          (when-let [image-src (:image snail)]
            [ui/view {:style {}}
             [ui/text {:style {:color    (:primary colors)
                               :fontSize (:small font-size)}} "I found this snail!"]
             [ui/image {:source (js/require "./images/cljs.png")}]])
          [ui/view {:style {:paddingVertical 15}}
           (into [ui/view]
             (mapv (fn [[k v]]
                     (when-not (blank? v)
                       [text-row k v])) snail-rows))]]]))))