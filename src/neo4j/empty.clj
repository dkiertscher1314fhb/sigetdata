(ns neo4j.empty
  (:require 
    [clojure.java.shell :as sh])
  (:require [clojure.pprint :refer [pprint]]))

; empty neo4j database
; run shell commands as sudo
; http://gokceng.wordpress.com/2011/12/29/running-shell-commands-with-java-as-root/
; add that lines to /etc/sudoers
;# for user
;YOUR_USER_NAME ALL= NOPASSWD: ALL
;# for group
;YOUR_GROUP_NAME ALL= NOPASSWD: ALL

(sh/sh "ls" "-l" "/opt/neo4j-community-2.0.0/data/graph.db/")
(sh/sh "sudo" "ls" "/opt/neo4j-community-2.0.0/data/graph.db/")
(sh/sh "sudo" "rm" "-r" "/opt/neo4j-community-2.0.0/data/graph.db/")
(sh/sh "/home/d/bin/neo4j-empty-db.sh")
