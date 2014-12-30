(ns histor.diff
  (:require [clojure.data :as data])
  (:require [histor.operator :as op]))

(defn diff
  ([past current] (diff past current []))
  ([past current ks]
   (for [[k v] current]
     (if-let [past-entry (get past k)]
       (if (and (map? past-entry) (map? v))
         (diff past-entry v (conj ks (name k)))
         (op/$set (conj ks (name k)) v past-entry))
       (op/$set (conj ks (name k)) v)))))
