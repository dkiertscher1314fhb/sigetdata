(ns sigetdata.core
  ; http://commons.apache.org/proper/commons-compress/apidocs/allclasses-noframe.html
  (:import [org.apache.commons.compress.compressors.bzip2 BZip2CompressorInputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.html
  (:import [org.apache.commons.compress.compressors.bzip2 BZip2CompressorOutputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/compressors/CompressorOutputStream.html
  (:import [org.apache.commons.compress.compressors.gzip GzipCompressorInputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/compressors/gzip/GzipCompressorInputStream.html
  (:import [org.apache.commons.compress.compressors.gzip GzipCompressorOutputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/compressors/gzip/GzipCompressorOutputStream.html

  (:require [clojure.java.io :as io])
  (:require [clojure.data.xml :as xml]) ;https://github.com/clojure/data.xml
  (:require [clojure.zip :refer [xml-zip]]) ;http://clojure.github.io/clojure/clojure.zip-api.html
  (:require [clojure.data.zip.xml :refer [xml-> xml1-> text]]) ; http://clojure.github.io/data.zip/#clojure.data.zip.xml http://clojure.github.io/data.zip/
  (:require [me.raynes.fs :as fs]) ; https://github.com/Raynes/fs
  (:require [clojure.pprint :refer [pprint]])
  (:require [clojure.java.jdbc :as jdbc])
  (:require [clojure.string :as s])
;  (:require [clojure.java.jdbc.deprecated :as jdbcd]) 
  (:gen-class :main true)
)



(defn bz2-reader
  "Returns a streaming Reader for the given compressed BZip2
  file. Use within (with-open)."
  [filename]
  (-> filename io/file io/input-stream BZip2CompressorInputStream. io/reader))

(defn gzip-reader
  "Returns a streaming Reader for the given compressed Gzip
  file. Use within (with-open)."
  [filename]
  (-> filename io/file io/input-stream GzipCompressorInputStream. io/reader))

(defn pprint-page
  "Process a wikipedia page, print the title"
  [page]
  (let [z (xml-zip page)
        title (xml1-> z :title text)
        page-text (xml1-> z :revision :text text)]
    (if true
      [(pprint title)])))

(defn visit-revision
  "Process a wikipedia page revision, print the attributes"
  [r]
  (let [revisor (xml-> r :contributor :username text)
        revisorip (xml-> r :contributor :ip text)
        revisorid (xml-> r :contributor :id text)
        timestamp (xml-> r :timestamp text) 
        ]
    {:revisor revisor
     :revisorip revisorip
     :revisorid revisorid
     :timestamp timestamp}
    ))

(defn visit-page
  "Process a wikipedia page, print the page"
  [page]
  (let [z (xml-zip page)
        title (xml1-> z :title text)
        pageid (xml1-> z :id text)
        page-text (xml1-> z :revision :text text)
        revisions (map visit-revision (xml-> z :revision))
        pageattributes {:title title
                        :pageid pageid
                        :revisions revisions}]
    ; page
;    (pprint pageattributes)
    (pprint revisions)
  ))
(defn wiki-pages
  "parse wikifile"
 [filename from max selector]
  ;todo later: choose decompression format according to file ending in filename
  (with-open [rdr (bz2-reader filename)]
    (dorun (->> (xml/parse rdr)
             :content
             (drop from)
             (take max)
             (map selector)))))

; clojure.java.io resource function nutzen fuer projektrelative Pfade
; eine große Datei jedoch nicht nach bin kopieren, deshalb hier den kompletten Pfad
; (def wikifile "R:/420/wikidumps/dewiki-latest-pages-articles.xml.bz2")
 (def wikifile "/run/media/d/TOSHIBA EXT/420/wikidumps/dewiki-latest-pages-articles.xml.bz2")
;(def wikifile "R:/420/wikidumps/dewiki-20131008-pages-meta-history1.xml.bz2") 
(def categorySqlFile "/run/media/d/TOSHIBA EXT/420/wikidumps/dewiki-20131008-categorylinks.sql.bz2")
;(def testSqlFile "/run/media/d/TOSHIBA EXT/420/wikidumps/dewiki-20131008-categorylinks.sql.bz2")
(def categorySqlFile "/home/d/Dokumente/dewiki-20131008-categorylinks.sql.bz2")


(def sqlstring "-- phpMyAdmin SQL Dump
-- version 4.0.4.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Erstellungszeit: 06. Dez 2013 um 13:42
-- Server Version: 5.6.11
-- PHP-Version: 5.5.3
SET SQL_MODE = \"NO_AUTO_VALUE_ON_ZERO\";
SET time_zone = \"+00:00\";
/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
--
-- Datenbank: `categorylinks`
--
CREATE DATABASE IF NOT EXISTS `categorylinks` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `categorylinks`;
-- --------------------------------------------------------
--
-- Tabellenstruktur für Tabelle `tabellea`
--
CREATE TABLE IF NOT EXISTS `tabellea` (
  `sa` int(11) NOT NULL,
  `sb` int(11) NOT NULL,
  `sc` int(11) NOT NULL,
  `sd` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
--
-- Daten für Tabelle `tabellea`
--
INSERT INTO `tabellea` (`sa`, `sb`, `sc`, `sd`) VALUES
(13, 13, 12, 12),
(13, 13, 12, 12),
(13, 13, 12, 12);
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;")

(defn filteremptylines [coll]
  (filter (fn [l] (not (empty? l))) coll)) 

(defn parseCategorySql
  "Wikipedia Category links File parse for PageID and CategoryName"
  [file]
  (with-open [rdr (bz2-reader file)]
    (s/join "\n" (doall (filteremptylines (line-seq rdr))))))

(parseCategorySql testSqlFile)

(defn -main
  [& args]
  (wiki-pages wikifile 1 10 visit-page)
  (parseCategorySql categorySqlFile))
  
(defn count-pages 
  "count number of pages in an xml"
 [filename]
  ;todo later: choose decompression format according to file ending in filename
  (with-open [rdr (bz2-reader filename)]
    (count (->> (xml/parse rdr)
             :content ))))

;(count-pages wikifile)
;3233673

; wikipedia categorylinks
; http://www.apachefriends.org/download.php?xampp-portable-win32-1.8.3-1-VC11.zip
; https://github.com/clojure/java.jdbc/

(def db-spec 
  {:subprotocol "mysql"
   :subname "//localhost:41308/categorylinks"
   :username "root"
   :password ""})


;(jdbc/get-connection db-spec)
; http://stackoverflow.com/questions/16949840/clojure-jdbc-how-to-use-pooled-connection-for-query

; create db
;(jdbc/db-do-prepared db-spec true  "CREATE DATABASE IF NOT EXISTS `categorylinks`
;  DEFAULT CHARACTER SET `latin1`
;  COLLATE `latin1_general_ci`;")

; create table from sql dump
;(jdbc/db-do-prepared db-spec true (s/join "\n" (parseCategorySql testSqlFile)))
;(jdbc/db-do-prepared db-spec true (s/replace (s/join "\n" (parseCategorySql testSqlFile)) "\"" "\\\""))
;(jdbc/db-do-prepared db-spec true (s/replace (s/join (parseCategorySql testSqlFile)) "\n" ""))

;(jdbc/db-do-prepared db-spec true  (parseCategorySql testSqlFile))


;(jdbc/db-do-prepared db-spec true "") 

;(jdbc/insert! db-spec :tabellea
;  {:sa 13 :sb 13 :sc 12 :sd 12})
;
;(jdbc/query db-spec
;  ["SELECT * FROM tabellea"])

;(jdbc/db-do-prepared db-spec (slurp "I:/wikidumps/dewiki-20131008-categorylinks.sql"))
;(jdbc/db-do-commands db-spec(parseCategorySql categorySqlFile))
;(jdbc/insert! db-spec :fruit
;  {:name "Apple" :appearance "rosy" :cost 24}
;  {:name "Orange" :appearance "round" :cost 49})
;; ({:generated_key 1} {:generated_key 2})
;
;(jdbc/query mysql-db
;  (s/select * :fruit (s/where {:appearance "rosy"}))
;  :row-fn :cost)

;http://www.w3schools.com/sql/sql_func_first.asp
(jdbc/query db-spec
  ["SELECT FIRST(cl_to) FROM categorylinks"])
