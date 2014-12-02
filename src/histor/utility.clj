(ns histor.utility
  (:import (clojure.lang Keyword)))

(defn data-key [coll id millis]
  (str :data coll id millis))

(defn info-key [coll id]
  (str :info coll id))
