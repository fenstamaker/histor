(ns histor.database
  (:require [histor.store.keyvalue :refer :all]
            [histor.store.internal :refer :all]
            [histor.utility :refer :all])
  (:require [taoensso.nippy  :as nippy]
            [clj-time.core   :as time]
            [pandect.algo.sha256 :refer :all])
  (:require [taoensso.timbre :as timbre]))

(timbre/refer-timbre)

(defn key-exists? [db k]
  (nil? (get db k)))

(defn save [db coll id object]
  (p :save-data
     (let [time      (p :get-cur-time   (time/now))
           info-key  (p :info-key-gen   (info-key coll id))
           data-key  (p :data-key-gen   (data-key coll id (time-str time)))
           cur-info  (p :get-cur-info   (or (nippy/thaw (get db info-key)) {:size 0 :checksums [] :latest nil}))
           data      (p :serialize-data (nippy/freeze object))]
       (p :saving-data (put db data-key data))
       (p :saving-info (put db info-key (nippy/freeze
                                          (-> cur-info
                                              (update-in [:size]      + 1)
                                              (update-in [:checksums] conj (sha256 data))
                                              (assoc-in  [:latest]    data-key))))))))

(defn delete [db coll id]
  (p :delete-data
     (let [info-key (p :info-key-gen (info-key coll id))
           cur-info (p :get-cur-info (get db info-key))]
       (p :saving-info (put db info-key
                            (nippy/freeze
                              (assoc-in cur-info [:deleted?] true)))))))

(defn findOne [db coll id]
  (p :get-data
     (let [info-key (p :info-key-gen (info-key coll id))
           cur-info (p :get-cur-info (get db info-key))]
       (p :find-one-data
          (if (and (:deleted? cur-info) (:latest cur-info))
            nil
            (get db (:latest cur-info)))))))