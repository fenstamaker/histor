(ns histor.store.keyvalue)

(defrecord KeyValueStoreInformation [name])

(defprotocol KeyValueStore
  "The unified protocol for any KeyValueStore used by Histor."
  (get     [this k]   "Retrieves the value for key _k_.")
  (delete  [this k]   "Delete the value for key _k_.")
  (put     [this k v] "Writes value _v_ to key _k_.")
  (post    [this k v] "Updates key _k_ to value _v_.")
  (open    [this f]   "Returns the KeyyValueStore with for given file _f_ ."))
