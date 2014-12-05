(ns histor.utility
  (:require [cli-time.core :as time]
            [cli-time.format :as f]))

(def separator (char 0))

(def time-format (f/formatters :basic-date-time))

(defn time-str [time]
  (f/unparse time-format time))

(defn data-key [id version]
  (keyword (str "99" separator (name id) separator version)))

(defn info-key [id]
  (keyword (str "00" separator (name id))))