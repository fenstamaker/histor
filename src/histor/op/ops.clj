(ns histor.op.ops
  (:require [clj-time.core :as time]))

(defn- event
  ([predicate subject] [predicate (time/now)])
  ([predicate subject object]
    [predicate subject object (time/now)]))

(defn $set  (partial event "$set"))
(defn $del  (partial event "$del"))
(defn $inc  (partial event "$inc"))
(defn $pop  (partial event "$pop"))
(defn $push (partial event "$push"))
(defn $pull (partial event "$pull"))