(ns histor.database.mongo
  (:require [monger.core :as db]
            [monger.core :as mc]
            [monger.operators :refer :all])
  (:require monger.joda-time)
  (:import [org.bson.types ObjectId]))

(def Nodes      "nodes")
(def Events     "events")
(def NodeEvents "nodeEvents")

(defn create [db col id base]
  (let [obj {:_id   id
             :base  base
             :diff  []
             :curr  base}]
    (mc/insert db col obj)))

(defn save-event [db id event]
  (mc/update db Nodes {:_id id} {$set {:current nil}})
  (mc/insert db NodeEvents {:subject id :event event}))

(defn get-node-events [db id]
  (doall (map :even (mc/find-maps db NodeEvents {:subject id}))))

(defn update-node [db id update]
  (save-event db Nodes id update))

(defn get-node [db ^String id]
  (mc/find-map-by-id db Nodes id))

(defn open []
  (db/get-db (db/connect) "histor"))
