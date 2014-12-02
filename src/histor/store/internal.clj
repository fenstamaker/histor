(ns histor.store.internal
  (:require [histor.store.keyvalue :refer :all]))

(def state (atom {}))

(deftype InternalStore [opts file]
  KeyValueStore
  (get [_ k]
    (get @state k))
  (delete [_ k]
    (reset! state (into (sorted-map-by >) (dissoc @state k))))
  (put [_ k v]
    (reset! state (into (sorted-map-by >) (assoc @state k v))))
  (post [this k v]
    (put this k v)))

