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
  (:require [clojure.java.jdbc :as jdbc]) ;https://github.com/clojure/java.jdbc
  (:require [clojure.string :as s])
  (:gen-class :main true)
)


; clojure.java.io resource function nutzen fuer projektrelative Pfade
; eine große Datei jedoch nicht nach bin kopieren, deshalb hier den kompletten Pfad
(def wikifile "I:/wikidumps/dewiki-latest-pages-articles.xml.bz2") 
(def categorySqlFile "I:/wikidumps/dewiki-20131008-categorylinks.sql.bz2")
(def testSqlFile "I:/wikidumps/categorylinks.sql.bz2")
(def testSqlFile2 "I:/wikidumps/categorylinkstest.sql.bz2")



; 
; Database access
;
(def db-spec 
  {:subprotocol "mysql"
   :subname "//localhost:3306/categorylinks"
   :username "root"
   :password ""
   :useUnicode "yes"
   :characterEncoding "utf8"})

; TODO: query um category und Artikel ID aus der categorylinks DB zu bekommen
(defn queryCategories
  "Query the categorylinks database for categories related to the given article ID"
  [pageID]
  (let [sqlquerystring (s/join ["SELECT CAST(cl_to AS CHAR) FROM categorylinks WHERE cl_from=" (.toString pageID)])] 
    (map (fn [c] (val (first c))) 
         (jdbc/query db-spec [sqlquerystring])))
  )

(queryCategories 3)



; 
; Reader for compressed Files
;
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

; 
; Access to compressed XML Files
;
(defn visit-revision
  "Process a single revision of a wikipedia page and return important attributes"
  [r]
  (let [revisor (xml-> r :contributor :username text)
        revisorip (xml-> r :contributor :ip text)
        revisorid (xml-> r :contributor :id text)
        timestamp (xml-> r :timestamp text)]
  
    {:revisor revisor
     :revisorip revisorip
     :revisorid revisorid
     :timestamp timestamp}
    ))

(defn visit-page
  "Process a wikipedia page by getting all relevant attributes"
  [page]
  (let [z (xml-zip page)
        title (xml1-> z :title text)
        pageid (xml1-> z :id text)
        page-text (xml1-> z :revision :text text)
        revisions (map visit-revision (xml-> z :revision))
        pageattributes {:title title
              :pageid pageid
              :revisions revisions}
        categories (queryCategories pageid)]
    (pprint categories) 
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



(defn -main
  [& args]
  (wiki-pages wikifile 6 1 visit-page))





; TODO: History und Category zusammenführen und als XML oder EDN rausschreiben in größeren Chunks







; SQL Category Dump laden über windows Commandline:
; mysql create database categorylinks
; mysql -u root categorylinks < dewiki-20131008-categorylinks.sql
;          User Datenbankname   .sql File
;(jdbc/insert! db-spec :tabellea
;  {:sa 13 :sb 13 :sc 12 :sd 12})
;(jdbc/query db-spec
;  ["SELECT * FROM tabellea"])
;(jdbc/insert! db-spec :fruit
;  {:name "Apple" :appearance "rosy" :cost 24}
;  {:name "Orange" :appearance "round" :cost 49})
;
; create table from sql dump
;(jdbc/db-do-prepared db-spec true (s/join "\n" (insertCategorySql testSqlFile)))
;(jdbc/db-do-prepared db-spec true (s/replace (s/join "\n" (insertCategorySql testSqlFile)) "\"" "\\\""))
;(jdbc/db-do-prepared db-spec true (s/replace (s/join (insertCategorySql testSqlFile)) "\n" ""))
;
;(defn filteremptylines [coll]
;  (filter (fn [l] (not (empty? l))) coll)) 
;
;(defn insertCategorySql
;  "insert Wikipedia Category SQLDump"
;  [file]
;  (with-open [rdr (bz2-reader file)]
;    (s/join "\n" (doall (filteremptylines (line-seq rdr))))))
;
;  
;(defn count-pages 
;  "count number of pages in an xml"
; [filename]
;  ;todo later: choose decompression format according to file ending in filename
;  (with-open [rdr (bz2-reader filename)]
;    (count (->> (xml/parse rdr)
;             :content ))))
;
;(defn pprint-page
;  "Process a wikipedia page, print the title"
;  [page]
;  (let [z (xml-zip page)
;        title (xml1-> z :title text)
;        page-text (xml1-> z :revision :text text)]
;    (if true
;      [(pprint title)])))
