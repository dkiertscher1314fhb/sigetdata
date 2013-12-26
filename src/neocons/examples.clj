(ns neocons.examples
  (:require 
    [clojure.java.shell :as sh]
    [clojurewerkz.neocons.rest :as nr]
    [clojurewerkz.neocons.rest.nodes :as nn]
    [clojurewerkz.neocons.rest.relationships :as nrl]
    [clojurewerkz.neocons.rest.batch :as batch]
    [clojurewerkz.neocons.rest.transaction :as tx]
    [clojurewerkz.neocons.rest.constraints :as nc]
    [clojurewerkz.neocons.rest.labels :as nl]
            )
  (:require [clojure.pprint :refer [pprint]]))

(nr/connect! "http://localhost:7474/db/data/")

; http://localhost:7474

; http://docs.neo4j.org/refcard/2.0/
; http://www.neo4j.org/learn
; http://www.fromdev.com/2013/10/Cypher-Query-Snippets-Neo4j-Development.html
; https://github.com/michaelklishin/neocons
; http://clojureneo4j.info/articles/guides.html

; batch insertion
(let [nodes (nn/create-batch [{:url "http://clojureneo4j.info" :domain "clojureneo4j.info"}
                                {:url "http://clojuremongodb.info" :domain "clojuremongodb.info"}
                                {:url "http://clojureriak.info" :domain "clojureriak.info"}])]
    ;; printing here will force the lazy response sequence to be evaluated
    (println nodes))

; Constraints
(defn constraints
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (do
    ; create an uniqueness constraint on a label and a property name
    (nc/create-unique "Person" :name)

    ; get an existing uniquness constraint on a label and a property name
    (println (nc/get-unique "Person" :name))

    ; get all existing uniquness constraints on a label
    (println (nc/get-unique "Person"))

    ; get all existing constraints on a label
    (println (nc/get-all "Person"))

    ; get all existing constraints for the whole graph db
    (println (nc/get-all))

    ; drop an existing uniqueness constraint on a label and a property name
    ;(nc/drop-unique "Person" :name)
    ))

; Node Labels (Neo4J 2.0+)   
(let [node (nn/create {:name "Clint Eastwood"})]
    ; Add a single label to the node
    (nl/add node "Person")
    ; or, add multiple labels to the node
    (nl/add node ["Person" "Actor"])

    ; replace the current labels with new ones
    (nl/replace node ["Actor" "Director"])

    ; remove a particular label
    (nl/remove node "Person")

    ; listing all labels for a node
    (println (nl/get-all-labels node))

    ; list all labels for the whole graph db
    (println (nl/get-all-labels))

    ; getting all nodes with a label
    (println (nl/get-all-nodes "Actor"))

    ; get nodes by label and property
    (println (nl/get-all-nodes "Person" :name "Client Eastwood")))

; using cypher
"START n=node(*) return count(n);"

(let [a (tx/statement "CREATE (n {props}) RETURN n" {:props {:name "Node 1"}})
        b (tx/statement "CREATE (n) RETURN n")]
    (println a)
    (println b))