(ns histor.op.ops)

(defn op [operation change]
  {:op     operation
   :time  (time/now)
   :change change})

(defn add [obj-state field value]
  (update-in obj-state [:oplog] conj (op :add {field value})))

(defn put [obj-state field value]
  (update-in obj-state [:oplog] conj (op :put {field value})))

(defn del  [obj-state field]
  (update-in obj-state [:oplog] conj (op :del field)))