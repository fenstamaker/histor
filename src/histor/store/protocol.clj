(ns histor.store.protocol
  (:import (java.awt Cursor)))

(defprotocol Store
  (open     [this])
  (snapshot [this])
  (close!   [this]))

(defprotocol Writable
  (safe-write! [this actions])
  (write! [this actions]))

(defprotocol Readable
  (read [this id]))

(defprotocol Cursor
  "Takes a key _(id)_ of an object and returns a cursor
  that is able to travel back and forward in time."
  (current  [this]    "Returns an iterator at on the current fact.")
  (first    [this]    "Returns an iterator at the first fact.")
  (iterate  [this t1] "Returns an iterator at time _t1_.")
  (as-seq   [this] [this i1] [this i1 i2]
            "Returns either the entire history
            or a segment of history as a sequence."))

(defprotocol Iterator
  (value     [this] "Returns the value of the current position of the iterator.")
  (next      [this] "Moves the iterator forward one.")
  (past      [this] "Moves the iterator back one.")
  (peek-next [this] "Returns the value of the next position of the iterator.")
  (peek-past [this] "Returns the value of the past position of the iterator."))