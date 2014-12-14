(ns histor.store.core
  (:require [histor.utility :refer :all]
            [histor.op.ops :as op]
            [histor.op.oplog :as oplog]
            [clj-time.core  :as time]))

(def state (ref {}))

(defn insert [id field value]
  (commute state update-in [id] op/add field value))

(defn replace [id field value]
  (commute state update-in [id] op/put field value))

(defn delete [id field value]
  (commute state update-in [id] op/del field value))

(defn replay [id]
  (let [info (get @state id)
        obj  (atom (:base info))
        fop  (atom (:oplog info))
        rop  (atom [])]
    (oplog/replay obj fop rop)))