(ns pack.redis.config
  "Monitors a cluster of Redis database."
  (:require [riemann.config :refer :all]
            [riemann.streams :refer :all]
            [riemann.folds :as folds]
            [riemann.common :refer [event]]
            [core.alert.config :refer [alert]]
            [core.engine.config :refer [time-series]]))

(let [index (index)]
  (streams
    ; package namespace tag
    (where (tagged "#redis")

      ; time-series of used memory in MB for each host
      (match :service "redis used_memory"
        (scale (/ 1 1024 1024)
          (time-series :groupby "host")))

      ; time-series of mem_fragmentation_ratio for each host
      (by :host
        (project [(service "redis used_memory_rss") (service "redis used_memory")]
          (smap folds/quotient
            (with :service "redis mem_fragmentation_ratio"
              (without [1 1.5]
                (throttle 5 3600
                  (alert :level "minor")))
              (time-series :groupby "host")))))

      ; time-series of instantaneous_ops_per_sec for each host
      (match :service "redis instantaneous_ops_per_sec"
        (time-series :groupby "host"))

      ; time-series of hit rate for each host
      (by :host
        (project [(service "redis keyspace_hits") (service "redis keyspace_misses")] 
          (smap (fn [events]
                  (let [fe (first events)
                        le (last events)
                        rate (/ (:metric fe) (+ (:metric fe) (:metric le)))]
                    (event {:service "redis hit_rate"
                            :host (:host fe)
                            :metric rate})))
            (time-series :groupby "host"))))

      ; count and index total number of hosts
      (with :service "redis hosts"
        (coalesce
          (smap folds/count
            (with :host nil
              index)))))))
