; Startphase
; Creating a git repo for Eclipse project and Pushing to Github
; http://www.youtube.com/watch?v=GMVQx2hzFng
; wechsle in den gewuenschten git branch
; https://github.com/Kunena/Kunena-Forum/wiki/Create-a-new-branch-with-git-and-manage-branches

; start coding
; starte den REPL ueber Rechtsklick auf Eclipseprojektordner Run as Clojure Application

; start unittest automation by evaluating the call to midje
(require 'midje.repl) ; https://github.com/marick/Midje
(midje.repl/autotest)

; https://github.com/marick/Midje/wiki
; https://github.com/marick/Midje/wiki/Future-facts
; top down testing
; https://github.com/marick/Midje/wiki/Top-down-testing
; https://github.com/marick/Midje/wiki/The-idea-behind-top-down-development
; https://github.com/marick/Midje/wiki/A-tutorial-introduction
; https://github.com/marick/Midje/wiki/Metaconstants
; https://github.com/marick/Midje/blob/master/test/as_documentation/prerequisites__the_basics.clj

; CCW changes the namespace for each evaluation to the namespace under which the code lies
; and after that switches back to the previous namespace just like pushd popd

; entferne vars von im REPL geladener Tests aus dem namespace

; Eclipse use external webbrowser for urls in eclipse
; https://support.tasktop.com/hc/en-us/articles/200435116-How-do-I-configure-Eclipse-to-use-an-external-browser-

; network connection over proxy
; Eclipse proxy configuration: Eclipse Window Preferences General Network Connections Manual
; Maven proxy configuration:
; Set-Proxy-Envvar-FHB
; Set-Proxy-Envvar-FHB -off

; multithreading
(def long-calculation (future (apply + (range 1e8))))
@long-calculation
(println "done")

(def bg (future (Thread/sleep 8000) (println "done1")))
(def bg (future (Thread/sleep 4000) (println "done2")))
(def bg (future (Thread/sleep 9000) (println "done3")))
(def bg (future (Thread/sleep 2000) (println "done4")))

