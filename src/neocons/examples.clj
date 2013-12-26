(ns neocons.examples
  (:require 
    [clojure.java.shell :as sh]
    [clojurewerkz.neocons.rest :as nr]
    [clojurewerkz.neocons.rest.nodes :as node]
    [clojurewerkz.neocons.rest.relationships :as relationship]
    [clojurewerkz.neocons.rest.constraints :as nodeCon]
    [clojurewerkz.neocons.rest.batch :as batch]
    [clojurewerkz.neocons.rest.transaction :as tx]
    [clojurewerkz.neocons.rest.constraints :as nc]
    [clojurewerkz.neocons.rest.labels :as nl]
            )
  (:require [clojure.pprint :refer [pprint]]))

(nr/connect! "http://localhost:7474/db/data/")



