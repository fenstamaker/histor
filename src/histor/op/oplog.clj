(ns histor.op.oplog
  (:require [histor.utility :refer :all]))

(defn- $set [base ks v]
  (if (coll? ks)
    (assoc-in base (map keyword ks) v)
    (assoc-in base [(keyword ks)] v)))

(defn- $del [base ks]
  (if (coll? ks)
    (dissoc-in base (map keyword ks))
    (dissoc-in base [(keyword ks)])))

(defn- $push [base ks v]
  "Adds all of _v_ in _base_ at _ks_. _v_ can be either a single
  value or a collection of values."
  (if (coll? ks)
    (update-in base (map keyword ks) push v)
    (update-in base [(keyword ks)]   push v)))

(defn- $pull
  "Removes all v in base at ks. _v_ can be either a single
  value or a collection of values."
  [base ks v]
  (if (coll? ks)
    (update-in base (map keyword ks) pull v)
    (update-in base [(keyword ks)]   pull v)))

(defn- $inc [base ks]
  (if (coll? ks)
    (update-in base (map keyword ks) inc)
    (update-in base [(keyword ks)]   inc)))

(defmulti patch (fn [_ op] (first op)))
(defmethod patch "$set"  [base [_ ks v]] ($set  base ks v))
(defmethod patch "$del"  [base [_ ks _]] ($del  base ks))
(defmethod patch "$push" [base [_ ks v]] ($push base ks v))
(defmethod patch "$pull" [base [_ ks v]] ($pull base ks v))
(defmethod patch "$inc"  [base [_ ks _]] ($inc  base ks))