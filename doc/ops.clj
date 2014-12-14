(ns histor.op.ops
  (:require [clj-time.core :as time]))

(def ^{:const true} $add :$add)
(def ^{:const true} $put :$put)
(def ^{:const true} $del :$del)

(defn op [operation change]
  {:op     operation
   :time  (time/now)
   :change change})

(defn add [obj-state field value]
  (update-in obj-state [:oplog] conj (op :add {field value})))

(defn put [obj-state field value]
  (update-in obj-state [:oplog] conj (op :put {field value})))

(defn del  [obj-state field]
  (update-in obj-state [:oplog] conj (op :del field)))


{$put [:field-name "new value"]}
{$del [:field-name]}

; All fields of objects will be namespaced
{:obj-name/field-name 1}

; All nested objects will be flatten
{:obj-name/field-name/subfield-name 1}

; Example object
{:_id     00
 :base    {}
 :meta    {:_deleted false}
 :current {:obj-name/field-name/subfield-name 1}
 :op-time [1234 1235 1240 1272]
 :oplog   [{:$add [:obj-name]}
           {:$put [:obj-name/field-name "test"]}
           {:$del [:obj-name/field-name]}
           {:$put [:obj-name/field-name/subfield-name 1]}]}

;; What if everything was a quadruple?
;; subject predicate object time
[:obj-name/field-name/subfield-name :$histor/is 1 1234]