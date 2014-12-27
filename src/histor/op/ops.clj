(ns histor.op.ops
  (:require [clj-time.core :as time]))

(defn- event
  ([predicate object] [predicate object (time/now)])
  ([predicate subject object]
    [predicate subject object (time/now)]))

(defn $set  (partial event "$set"))
(defn $del  (partial event "$del"))
(defn $inc  (partial event "$inc"))
(defn $dec  (partial event "$dec"))
(defn $push (partial event "$push"))
(defn $pull (partial event "$pull"))