
;http://clojuredocs.org/clojure_core/clojure.java.io/file

; wikiparse.clj https://gist.github.com/kornysietsma/5939456
; Parsing XML in Clojure http://clojure-doc.org/articles/tutorials/parsing_xml_with_zippers.html

; * Vorgehen
; Datei entpacken b2zip, gzip, 7zip
; Anzahl der Artikel ermitteln
; die xml mit clojure.data.xml (StAX) in Chunks (Artikel) aufteilen
; n Artikel im EDN-Format serialisieren und komprimiert abspeichern
; Chunks paralellisiert verarbeiten

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

;; Zippers allow you to easily traverse a data structure

;http://stackoverflow.com/questions/18093154/clojure-data-xml-error
;(xml/parse (io/reader (io/resource "example.nzb")))

* TODO Clojure XML parsen [1/8]                           :systemintegration:
  SCHEDULED: <2013-11-29 Fr> DEADLINE: <2013-11-29 Fr>
  - [X] connect to compressed XML file
  - [-] analysiere eine repräsentative Wikiseite
    - [X] get pages from n to m
    - [ ] get a specific page and prove it
  - [ ] extrahiere Merkmale aus der Wikipage
    - [ ] find relevant attributes in page and its history
      - [ ] Kategorien
    - [ ] filter edn testdriven
    - [ ] aggregate attributes
  - [ ] Datenstruktur der Merkmale entwerfen
  - [ ] erzeuge Aussagen aus den extrahierten Merkmalen in Tripleform
    - [ ] form facts into triples
  - [ ] chunk XML file into pages with partition
  - [ ] partition large xml file into compressed chunks
    - [ ] write to edn or xml, textwriter, b2zip
    - [ ] connect to self compressed edn file
  - [ ] count pages
    - [ ] with timer
    - [ ] on compressed edn chunks in parallel on multiple cores
    - [ ] in a thread


