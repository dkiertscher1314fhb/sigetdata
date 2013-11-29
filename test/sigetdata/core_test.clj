(ns sigetdata.core-test
  (:require [midje.sweet :refer :all])
  (:require [clojure.test :refer :all])
  (:require [sigetdata.core :refer :all])
  (:require [me.raynes.fs :as fs]))

(facts "copy of wikifile available"
       (fact (fs/exists? wikifile) => true))
