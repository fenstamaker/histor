(ns histor.patch
  (:require [histor.utility :refer :all])
  (:require [clj-time.core :as time]))

(defrecord Patch [diff time])

(defn- $set [base ks v]
  (if (coll? ks)
    (assoc-in base (map keyword ks) v)
    (assoc-in base [(keyword ks)] v)))

(defn- $del [base ks]
  (if (coll? ks)
    (dissoc-in base (map keyword ks))
    (dissoc-in base [(keyword ks)])))

(defmulti  patch (fn [_ p] (:op (:diff p))))
(defmethod patch "$set" [base {{:keys [keys value]} :diff}]
  ($set base keys value))
(defmethod patch "$del" [base {{:keys [keys value]} :diff}]
  ($del base keys value))

(defn create-patch [diff]
  (Patch. diff (time/now)))
