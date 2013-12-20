(ns sigetdata.core-test
  (:require [midje.sweet :refer :all])
  (:require [clojure.test :refer :all])
  (:require [sigetdata.core :refer :all])
  (:require [me.raynes.fs :as fs]))

; test naming convention
; function name - scenario - expected result / behaviour
(facts "wikifile - for testing - available"
       (fact (fs/exists? wikifile) => true))

