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
  (:gen-class :main true))


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
        page {:title title
              :pageid pageid
              :revisions revisions}]
    ; page
    (pprint page) 
  ))
(defn wiki-pages
  "pars wikifile"
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
; (def wikifile "C:/Users/Host/eclipse_workspace_sysint/sigetdata/tmp/dewiki-latest-pages-articles.xml.bz2")
(def wikifile "R:/420/wikidumps/dewiki-20131008-pages-meta-history1.xml.bz2") 

(defn -main
  [& args]
  (wiki-pages wikifile 1 1 visit-page))

(defn count-pages 
  "count number of pages in an xml"
 [filename]
  ;todo later: choose decompression format according to file ending in filename
  (with-open [rdr (bz2-reader filename)]
    (count (->> (xml/parse rdr)
             :content ))))

;(count-pages wikifile)
;3233673

