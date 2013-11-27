(ns sigetdata.core
  (:import [org.apache.commons.compress.compressors.bzip2 BZip2CompressorInputStream]) ; http://commons.apache.org/proper/commons-compress/apidocs/org/apache/commons/compress/archivers/
  (:require [clojure.java.io :as io])
  (:require [clojure.data.xml :as xml]) ;https://github.com/clojure/data.xml
  (:require [clojure.zip :refer [xml-zip]]) ;http://clojure.github.io/clojure/clojure.zip-api.html
  (:require [clojure.data.zip.xml :refer [xml-> xml1-> text]]) ;http://clojure.github.io/data.zip/
  (:require [me.raynes.fs :as fs])
  (:gen-class :main true))

;https://gist.github.com/kornysietsma/5939456#file-wikiparse-clj-L11

;; https://www.refheap.com/

;; http://commons.apache.org/proper/commons-compress/apidocs/allclasses-noframe.html
;sevenz/SevenZArchiveEntry.html
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

; Vorgehen
; die xml mit java.xml.sachs in chunks (Artikel) aufteilen

; jeden Artikel einzeln mit clojure.data.xml realisieren und Daten selektieren mit clojure get-in clojure 
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
