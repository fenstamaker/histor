(ns histor.utility
  (:require [cli-time.core :as time]
            [cli-time.format :as f]))

(def separator ".$.")

(def time-format (f/formatters :basic-date-time))

(defn time-str [time]
  (f/unparse time-format time))

(defn data-key [coll id millis]
  (str :data separator
       coll  separator
       id    separator
       millis))

(defn info-key [coll id]
  (str :info separator
       coll  separator
       id))
