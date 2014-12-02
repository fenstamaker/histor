(defproject histor "0.1.0-SNAPSHOT"
  :description "A prototype immutable database built ontop of LevelDB."
  :url "http://fenstamaker.com/histor"
  :license {:name "Apache License, Version 2.0"
            :url "http://www.apache.org/licenses/"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [com.taoensso/nippy "2.7.1"]
                 [com.netflix.hystrix/hystrix-clj "1.4.0-RC5"]
                 [com.taoensso/nippy "2.7.1"]
                 [com.taoensso/timbre "3.3.1"]
                 [pandect "0.4.1"]
                 [clj-time "0.8.0"]
                 ])
