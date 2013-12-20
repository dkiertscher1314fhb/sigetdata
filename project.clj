(defproject sigetdata "0.1.0-SNAPSHOT"
  :description "parsing wikipedia dump compressed xml files"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/data.zip "0.1.1"]
                 [org.clojure/data.xml "0.0.7"]
                 [me.raynes/fs "1.4.4"]
                 [mysql/mysql-connector-java "5.1.27"]
                 [org.clojure/java.jdbc "0.3.0-beta2"]
                 [clojurewerkz/neocons "2.0.0"]]
  :profiles {:dev {:dependencies [[midje "1.6.0"]]
                   :plugins [[lein-midje "3.0.0"]]}})

