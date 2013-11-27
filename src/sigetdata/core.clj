(ns sigetdata.core
  (:require [clojure.java.io :as io]
            [clojure.data.xml :as xml]
            [clojure.zip :refer [xml-zip]] ;http://clojure.github.io/clojure/clojure.zip-api.html
            [clojure.data.zip.xml :refer [xml-> xml1-> text]] ;http://clojure.github.io/data.zip/
            [me.raynes.fs :as fs])
  (:import [org.apache.commons.compress.compressors.gzip GzipCompressorInputStream]
           ;; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/archivers/sevenz/SevenZFile.html#SevenZFile%28java.io.File,%20byte[]%29
           [org.apache.commons.compress.archivers.sevenz.SevenZFile]
           ;; http://sevenzipjbind.sourceforge.net/first_steps.html
           [net.sf.sevenzipjbinding.SevenZip]
           [net.sf.sevenzipjbinding.SevenZipNativeInitializationException]
           [net.sf.sevenzipjbinding.ISequentialOutStream]
           [net.sf.sevenzipjbinding.SevenZipException]
           [net.sf.sevenzipjbinding.simple.ISimpleInArchiveItem])
  ;;org.apache.commons.compress.compressors.SevenZFile
  (:gen-class :main true))


(defn sevenzip-reader
  "Returns a streaming Reader for the given compressed BZip2
  file. Use within (with-open)."
  [filename]
  (-> filename io/file io/input-stream GzipCompressorInputStream. io/reader))

(defn bz2-reader
  "Returns a streaming Reader for the given compressed BZip2
  file. Use within (with-open)."
  [filename]
  (-> filename io/file io/input-stream GzipCompressorInputStream. io/reader))

(defn process-music-artist-page
  "Process a wikipedia page, print the title if it's a musical artist"
  [page]
  (let [z (xml-zip page)
        title (xml1-> z :title text)
        page-text (xml1-> z :revision :text text)]
    (if true
      [(println page)])))

(defn wiki-music-artists
  "parse up to [max] pages from a wikipedia dump, print out those that are musical artists"
  [filename max]
  ;;choose decompression format according to file ending in filename
  (with-open [rdr (bz2-reader filename)]
    (dorun (->> (xml/parse rdr)
  :content
                (take max)
                (map process-music-artist-page)))))

;; clojure.java.io resource function nutzen fuer projektrelative Pfade
(def wikifile "C:/wikidumps/dewiki-latest-pages-articles.xml.bz2")
(def wikifile2 "C:/wikidumps/dewiki-20131008-stub-articles.xml.gz")
(def wikifile3 "C:/wikidumps/dewiki-20131008-stub-articlest.7z")
(def wikifile4 "R:/420/wikidumps/dewiki-20131008-pages-meta-history1.xml.bz2") 
(def wikifile5 "C:/Users/Host/eclipse_workspace_sysint/sigetdata/dev-resources/dewiki-latest-pages-articles.xml.bz2")

(defn -main
  [& args]
  (wiki-music-artists wikifile2 1))

;; versuche Funktionen aus Bibliotheken zu verwenden

;(.SevenZFile (io/file wikifile3))
;;http://clojuredocs.org/clojure_core/clojure.java.io/file
(io/file wikifile3)
;; https://github.com/Raynes/fs
(fs/file? wikifile3)
(fs/exists? wikifile3)
(fs/extension wikifile3)
(fs/parent wikifile3)
(fs/parents wikifile3)
(fs/path-ns wikifile3)
(fs/size wikifile3)
(fs/split wikifile3)
(fs/split-ext wikifile3)
(fs/mod-time wikifile3)
(fs/name wikifile3)
(fs/normalized-path wikifile3)
(fs/writeable? wikifile3)
(fs/list-dir "C:\\wikidumps")
(def wikifile3 "C:/wikidumps/dewiki-20131008-stub-articlest.7z")
(fs/list-dir (fs/parent wikifile3))
(fs/split wikifile3)
(fs/split wikifile3)
(fs/split wikifile3)
(fs/split wikifile3)

;; https://www.refheap.com/

;; http://commons.apache.org/proper/commons-compress/apidocs/allclasses-noframe.html
;; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.html
;; http://sevenzipjbind.sourceforge.net/snippets/GetNumberOfItemsInArchive.java
;; http://citizen428.net/blog/2009/09/06/using-java-libraries-from-clojure

;; Zippers allow you to easily traverse a data structure

;http://stackoverflow.com/questions/18093154/clojure-data-xml-error
;(xml/parse (io/reader (io/resource "example.nzb")))

(-> "example.nzb" io/file xml/parse)
(defn nzb->map
  "filters an xml example file"
  [input]
  ((-> input io/reader xml/parse) 
    ))

; wikiparse.clj https://gist.github.com/kornysietsma/5939456
; Parsing XML in Clojure http://clojure-doc.org/articles/tutorials/parsing_xml_with_zippers.html
