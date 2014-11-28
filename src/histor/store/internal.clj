(ns histor.store.internal
  (:require [histor.store.keyvalue :refer :all]))

(def- state (atom {}))

(deftype InternalStore [file]
  KeyValueStore
  (get [_ k]
    (get @state k nil))
  (delete [_ k]
    (swap! state dissoc k))
  (put [_ k v]
    (swap! state assoc k v))
  (post [this k v]
    (put this k v)))

