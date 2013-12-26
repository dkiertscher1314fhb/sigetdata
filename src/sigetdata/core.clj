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
