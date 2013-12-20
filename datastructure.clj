(ns datastructure)

; Beispiel fuer Merkmale einer Seite
{:tag :page,
 :content
 ({:tag :title,
   :content ("Alan Smithee")}
  {:tag :id
   :content ("1")}
  {:tag :revision,
   :content
   ({:tag :timestamp, 
     :content ("2001-05-30T11:37:26Z")}
    {:tag :contributor,
     :content
     ({:tag :username,
       :content ("bln2-t3-2.mcbone.net")}
      {:tag :id, 
       :content ("0")})}
    {:tag :contributor,
     :content 
     ({:tag :ip,
       :content ("84.44.154.146")})})})}

; Merkmale einer Seite    
 :page
   :title "Alan Smithee"
   :id "1"
   :revision
     :time
       :timestamp "2001-05-30T11:37:26Z"
       :year
        :yearmonth
        :yearmonthday
        :yearmonthdayhour
     :contributor
       :username "bln2-t3-2.mcbone.net"
       :id "0"
       :ip "84.44.154.146"

; Datenstruktur der Merkmale entwerfen

[
 [{:title "Alan Smithee"
   :id "1"}
   [
     [{:time
       [{:timestamp "2001-05-30T11:37:26Z"}
        :year
        :yearmonth
        :yearmonthday
        :yearmonthdayhour]}
     {:contributor
       {:username "bln2-t3-2.mcbone.net"
        :id "0"
        :ip "84.44.154.146"}}]
   ]
    ]
 ]

; Triple
; http://de.slideshare.net/neo4j/data-modeling-with-neo4j
:title :editedby :contributor
:contributor :edited :title

:timestamp :edit :title
:title :editedon :time

:timestamp :editing :contributor
:contributor :editing :time

; Triple Abstraktion auf :timestamp
; http://blog.neo4j.org/2012/02/modeling-multilevel-index-in-neoj4.html
:yearmonth :has :yearmonthday
:yearmonthday :belongsto :yearmonth

:year :has :yearmonth
:yearmonth :belongsto :year

:yearmonthday :has :yearmonthdayhour
:yearmonthdayhour :belongsto :yearmonthday


