(ns sigetdata.core
  (:require [clojure.java.io :as io]
            [clojure.data.xml :as xml]
            [clojure.zip :refer [xml-zip]] ;http://clojure.github.io/clojure/clojure.zip-api.html
            [clojure.data.zip.xml :refer [xml-> xml1-> text]] ;http://clojure.github.io/data.zip/
            [me.raynes.fs :as fs])
  (:import [org.apache.commons.compress.compressors.gzip GzipCompressorInputStream]
           ;; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/archivers/sevenz/SevenZFile.html#SevenZFile%28java.io.File,%20byte[]%29
           )
  ;;org.apache.commons.compress.compressors.SevenZFile
  (:gen-class :main true))

;; https://www.refheap.com/

;; http://commons.apache.org/proper/commons-compress/apidocs/allclasses-noframe.html
;; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/archivers/sevenz/SevenZArchiveEntry.html
;; http://sevenzipjbind.sourceforge.net/snippets/GetNumberOfItemsInArchive.java
;; http://citizen428.net/blog/2009/09/06/using-java-libraries-from-clojure

;; Zippers allow you to easily traverse a data structure

;http://stackoverflow.com/questions/18093154/clojure-data-xml-error
;(xml/parse (io/reader (io/resource "example.nzb")))



; wikiparse.clj https://gist.github.com/kornysietsma/5939456
; Parsing XML in Clojure http://clojure-doc.org/articles/tutorials/parsing_xml_with_zippers.html

