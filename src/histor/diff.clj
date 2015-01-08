(ns histor.diff
  (:require [clojure.data :as data])
  (:require [histor.operator :as op]))

(defrecord Diff [op keys value old])

(defn- generate-diff
  ([operation k new-value]
   (generate-diff operation k new-value nil))
  ([operation k new-value old-value]
   (Diff. operation k new-value old-value)))

(def $set  (partial generate-diff "$set"))
(def $del  (partial generate-diff "$del"))

(defn diff
  ([past current] (diff past current []))
  ([past current ks]
   (for [[k v] current]
     (if-let [past-entry (get past k)]
       (if (and (map? past-entry) (map? v))
         (diff past-entry v (conj ks (name k)))
         ($set (conj ks (name k)) v past-entry))
       ($set (conj ks (name k)) v)))))
