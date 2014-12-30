(ns histor.operator
  (:require [clj-time.core :as time]))

(defrecord Diff [op keys value old])

(defn- generate-diff
  ([operation k new-value]
   (generate-patch operation k new-value nil))
  ([operation k new-value old-value]
   (Diff. operation k new-value old-value)))

(def $set  (partial generate-diff "$set"))
(def $del  (partial generate-diff "$del"))
