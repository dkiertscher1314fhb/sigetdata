(ns neocons.examples
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as node]
            [clojurewerkz.neocons.rest.relationships :as relationship]
            [clojurewerkz.neocons.rest.constraints :as nodeCon]
            [clojurewerkz.neocons.rest.batch :as batch]
            [clojurewerkz.neocons.rest.transaction :as tx]
            [clojurewerkz.neocons.rest.constraints :as nc]
            [clojurewerkz.neocons.rest.labels :as nl])
  (:require [clojure.pprint :refer [pprint]]))

(nr/connect! "http://localhost:7474/db/data/")

(def node (node/create {:name "Clint Eastwood"}))


  
  ; create a node
  
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
    (println (nl/get-all-nodes "Person" :name "Client Eastwood"))

    
(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  ; create a node
  (let [node (node/create {:name "Clint Eastwood"})]
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
    (println (nl/get-all-nodes "Person" :name "Client Eastwood"))))

(-main)
;; connects to the default Neo4J Server host/port/path
(nr/connect! "http://localhost:7474/db/data/")

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (node/create {:name "amy"})
        bob (node/create {:name "bob"})
        rel (relationship/create amy bob :friend {:source "college"})]
    (println rel)))
(-main)

    
    

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (node/create {:username "amy"})
        bob (node/create {:username "bob"})
        rel (relationship/create amy bob :friend {:source "college"})]
    (println rel)))



(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  ;; efficiently executes a batch of operations in a single HTTP request, can handle hunreds of thousands or even millions of
  ;; nodes with reasonably small heaps. Returns a lazy sequence, for it with clojure.core/doall
  ;; if you want to parse & calculate the entire response at once.
  (let [ops [{:method "POST"
              :to     "/node?unique=true"
              :body   {:name "Anders"}}]
        res (doall (batch/perform ops))]
    ;; printing here will force the lazy response sequence to be evaluated
    (println res)))
(-main)



(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (tx/in-transaction
    (tx/statement "CREATE (n {props}) RETURN n" {:props {:name "Node 1"}})
    (tx/statement "CREATE (n {props}) RETURN n" {:props {:name "Node 2"}})))

(-main)

(node/get (:id {:username "amy"}))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (node/create {:username "amy"})
        bob (node/create {:username "bob"})
        rel (relationship/create amy bob :friend {:source "college"})]
    (pprint (relationship/get (:id rel)))))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (node/create {:username "amy"})
        bob (node/create {:username "bob"})
        _   (relationship/create amy bob :friend {:source "college"})]
    (pprint (:id (first (relationship/outgoing-for amy :types [:friend]))))))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (node/create {:username "amy"})
        bob (node/create {:username "bob"})
        _   (relationship/create amy bob :friend {:source "college"})
         _   (relationship/create bob amy :friend {:source "college"})]
    (pprint [(relationship/outgoing-for bob :types [:friend])
            (relationship/incoming-for bob :types [:friend])])))

(-main)

(ns neocons.examples
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as node]
            [clojurewerkz.neocons.rest.relationships :as relationship]
            [clojurewerkz.neocons.rest.cypher :as cy]))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (node/create {:username "amy"})
        bob (node/create {:username "bob"})
        _   (relationship/create amy bob :friend {:source "college"})
        res (cy/tquery "START person=node({sid}) MATCH person-[:friend]->friend RETURN friend" {:sid (:id amy)})]
    (pprint res)))

(-main)

(+ 12 (* 12 (+ 1 12 365 (* 2 365 24))))
(* 3233673 (+ (* 200 6) 2 6))
