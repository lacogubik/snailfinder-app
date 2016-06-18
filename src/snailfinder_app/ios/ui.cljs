(ns snailfinder-app.ios.ui
  (:require [reagent.core :as r :refer [atom]]
            [clojure.string :refer [blank?]]
            [re-frame.core :refer [subscribe dispatch dispatch-sync]]
            [snailfinder-app.handlers]
            [snailfinder-app.subs]))

(def ReactNative (js/require "react-native"))

(def app-registry (.-AppRegistry ReactNative))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
(def navigator (r/adapt-react-class (.-NavigatorIOS ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))

(def scroll
  (r/adapt-react-class (.-ScrollView ReactNative)))

(def touchable
  (r/adapt-react-class (.-TouchableWithoutFeedback ReactNative)))
