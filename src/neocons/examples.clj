(ns neocons.examples
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.relationships :as nrl])
  (:require [clojure.pprint :refer [pprint]]))


;; connects to the default Neo4J Server host/port/path
(nr/connect! "http://localhost:7474/db/data/")

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})
        bob (nn/create {:username "bob"})
        rel (nrl/create amy bob :friend {:source "college"})]
    (println rel)))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})]
    (println (:data (nn/get 17)))))

(pprint (nn/get 17))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})]
    (println (nn/get (:id amy)))))

(-main)

(nn/get (:id {:username "amy"}))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})
        bob (nn/create {:username "bob"})
        rel (nrl/create amy bob :friend {:source "college"})]
    (pprint (nrl/get (:id rel)))))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})
        bob (nn/create {:username "bob"})
        _   (nrl/create amy bob :friend {:source "college"})]
    (pprint (:id (first (nrl/outgoing-for amy :types [:friend]))))))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})
        bob (nn/create {:username "bob"})
        _   (nrl/create amy bob :friend {:source "college"})
         _   (nrl/create bob amy :friend {:source "college"})]
    (pprint [(nrl/outgoing-for bob :types [:friend])
            (nrl/incoming-for bob :types [:friend])])))

(-main)

(ns neocons.examples
  (:require [clojurewerkz.neocons.rest :as nr]
            [clojurewerkz.neocons.rest.nodes :as nn]
            [clojurewerkz.neocons.rest.relationships :as nrl]
            [clojurewerkz.neocons.rest.cypher :as cy]))

(defn -main
  [& args]
  (nr/connect! "http://localhost:7474/db/data/")
  (let [amy (nn/create {:username "amy"})
        bob (nn/create {:username "bob"})
        _   (nrl/create amy bob :friend {:source "college"})
        res (cy/tquery "START person=node({sid}) MATCH person-[:friend]->friend RETURN friend" {:sid (:id amy)})]
    (pprint res)))

(-main)

(+ 12 (* 12 (+ 1 12 365 (* 2 365 24))))
(* 3233673 (+ (* 200 6) 2 6))
