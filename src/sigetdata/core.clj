(ns sigetdata.core
  ; http://commons.apache.org/proper/commons-compress/apidocs/allclasses-noframe.html
  (:import [org.apache.commons.compress.compressors.bzip2 BZip2CompressorInputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/compressors/bzip2/BZip2CompressorInputStream.html
  (:import [org.apache.commons.compress.compressors.gzip GzipCompressorInputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/compressors/gzip/GzipCompressorInputStream.html

  (:require [clojure.java.io :as io])
  (:require [clojure.data.xml :as xml]) ;https://github.com/clojure/data.xml
  (:require [clojure.zip :refer [xml-zip]]) ;http://clojure.github.io/clojure/clojure.zip-api.html
  (:require [clojure.data.zip.xml :refer [xml-> xml1-> text]]) ;http://clojure.github.io/data.zip/
  (:require [me.raynes.fs :as fs]) ; https://github.com/Raynes/fs
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

(defn process-music-artist-page
  "Process a wikipedia page, print the title"
  [page]
  (let [z (xml-zip page)
        title (xml1-> z :title text)
        page-text (xml1-> z :revision :text text)]
    (if true
      [(println page)])))

(defn wiki-music-artists
  "parse up to [max] pages from a wikipedia dump, print out those that are musical artists"
  [filename max]
  ;todo later: choose decompression format according to file ending in filename
  (with-open [rdr (bz2-reader filename)]
    (dorun (->> (xml/parse rdr)
             :content
             (take max)
             (map process-music-artist-page)))))

; clojure.java.io resource function nutzen fuer projektrelative Pfade
; eine große Datei jedoch nicht nach bin kopieren, deshalb hier den kompletten Pfad
(def wikifile "C:/Users/Host/eclipse_workspace_sysint/sigetdata/tmp/dewiki-latest-pages-articles.xml.bz2")

(defn -main
  [& args]
  (wiki-music-artists wikifile 1))

;;http://clojuredocs.org/clojure_core/clojure.java.io/file

; wikiparse.clj https://gist.github.com/kornysietsma/5939456
; Parsing XML in Clojure http://clojure-doc.org/articles/tutorials/parsing_xml_with_zippers.html

; * Vorgehen
; Datei entpacken b2zip, gzip, 7zip

; die xml mit clojure.data.xml (StAX) in chunks (Artikel) aufteilen

; jeden Artikel einzeln mit clojure.data.xml realisieren und Daten selektieren mit clojure get-in clojure
; oder selektieren mit Zipper
; fuer den stream einen reader erzeugen mit:
; clojure.java.io/reader und writer erzeugt reader und writer
; den reader an data.xml geben

; https://gist.github.com/fmw/5490159
; https://gist.github.com/kornysietsma/5939456#file-wikiparse-clj-L11

; read first n elements
; check memory consumption and execution time
; count all elements
; read first then read last and then first again
; explore one element (page)


; https://www.refheap.com/

; sevenz/SevenZArchiveEntry.html
;; http://sevenzipjbind.sourceforge.net/snippets/GetNumberOfItemsInArchive.java
;; http://citizen428.net/blog/2009/09/06/using-java-libraries-from-clojure

;; Zippers allow you to easily traverse a data structure

;http://stackoverflow.com/questions/18093154/clojure-data-xml-error
;(xml/parse (io/reader (io/resource "example.nzb")))



