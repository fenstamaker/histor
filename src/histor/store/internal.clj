(ns histor.store.internal
  (:import (java.io Closeable))
  (:require [histor.store.protocol :refer :all]
            [histor.utility :refer :all]
            [clj-time.core :as time]))

(defrecord DataIterator [state key]
  Iterator
  (value [this]
    (get (:state this) (:key this)))
  )

(defrecord InfoCursor [state id]
  Cursor
  (current [this]
    (when-let [data-key (first (:versions (get (info-key (:id this)) (:state this))))]
      (DataIterator. (:state this) data-key)))
  (first [this]
    (when-let [data-key (last  (:versions (get (info-key (:id this)) (:state this))))]
      (DataIterator. (:state this) data-key)))
  (iterate [this key]
    (DataIterator. (:state this) key))
  (as-seq [this]
    (map (partial DataIterator. (:state this))
         (:versions (get (info-key (:id this)) (:state this))))))

(defrecord ReadTransaction [snapshot]
  Closeable
  (close [this] nil)
  Readable
  (read [this id]
    (InfoCursor. (:snapshot this) id)))

(defrecord WriteTransaction [state]
  Closeable
  (close  [this] nil)
  Writable
  (safe-write! [this actions]
    (let [version (time-str (time/now))
          finish? (ref false)]
      (dosync
        (doseq [[k v] actions]
          (alter (:state this)
                 #(assoc-in  % [(data-key k version)] v))
          (alter (:state this)
                 #(update-in % [(info-key k) :versions] cons version))
          (ref-set finish? true)))
      (if @finish?
        {:result "success"}
        {:result "failure"})))
  (write! [this actions]
    (let [version (time-str (time/now))]
      (dosync
        (doseq [[k v] actions]
          (commute (:state this)
                   #(assoc-in  % [(data-key k version)] v))
          (commute (:state this)
                   #(update-in % [(info-key k) :versions] cons version))))
      {:result "success"}))
  Readable
  (read [this id]
    (read (ReadTransaction. (subseq @(:state this)
                               >= (data-key id nil)
                               <= (data-key id "z"))))))

(defrecord InternalStore [state]
  Closeable
  (close [this] nil))

(defn create-store []
  (InternalStore. (ref {})))

(defn open-transaction [store]
  (WriteTransaction. (:state store)))

(defn open-snapshot [store]
  (ReadTransaction. @(:state store)))

