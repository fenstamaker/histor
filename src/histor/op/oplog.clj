(ns histor.op.oplog)

(defn- add [obj {:keys [change]}]
  (assoc obj (key change) (val change)))

(defn- put [obj {:keys [change]}]
  (merge obj change))

(defn- del [obj {:keys [change]}]
  (dissoc obj change))

(defmulti  patch :op)
(defmethod patch :add [obj op] (add obj op))
(defmethod patch :put [obj op] (put obj op))
(defmethod patch :del [obj op] (del obj op))

(defn replay [obj oplog]
  (map (partial patch obj) oplog))