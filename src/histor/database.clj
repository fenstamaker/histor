(ns histor.database
  (:require [histor.store.keyvalue :refer :all]
            [histor.store.internal :refer :all]
            [histor.utility :refer :all])
  (:require [taoensso.nippy :as nippy]
            [clj-time.core  :as time]
            [pandect.algo.sha256 :refer :all]))

(defn key-exists? [db k]
  (nil? (get db k)))

(defn save [db coll id object]
  (let [time      (time/now)
        info-key  (info-key coll id)
        data-key  (data-key coll id time)
        cur-info  (or (get db info-key) {:size 0 :checksums [] :latest nil})
        data      (nippy/freeze object)]
    (put db data-key data)
    (put db info-key (nippy/freeze
                       (-> cur-info
                           (update-in [:size]      + 1)
                           (update-in [:checksums] conj (sha256 data))
                           (assoc-in  [:latest]    time))))))