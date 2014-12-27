(ns histor.utility
  (:import (java.util Collection)))

(defn in?
  "Checks if value is in coll. Returns value if found in coll, else return nil."
  [coll value]
  (some #(= value %) coll))

(defn push
  "Adds all of _vs_ to _coll_."
  [coll vs]
  (if (coll? vs)
    (apply (partial conj coll) vs)
    (conj coll vs)))

(defn pull
  "Removes all of _vs_ to _coll_."
  [coll vs]
  (if (coll? vs)
    (remove (partial in? vs) coll)
    (remove (partial = vs)   coll)))

(defn dissoc-in
  "Dissociates an entry from a nested associative structure returning a new
  nested structure. keys is a sequence of keys. Any empty maps that result
  will not be present in the new structure. Taken from clojure.core.incubator."
  [m [k & ks :as keys]]
  (if ks
    (if-let [nextmap (get m k)]
      (let [newmap (dissoc-in nextmap ks)]
        (if (seq newmap)
          (assoc m k newmap)
          (dissoc m k)))
      m)
    (dissoc m k)))

(defn collapse [coll prefix]
  "Flattens all nested maps into a simple k-v map."
  (apply merge
         (map (fn [[k v]]
                (let [k2 (keyword (name prefix) (name k))]
                  (if (map? v)
                    (collapse v k2)
                    {k2 v})))
              coll)))