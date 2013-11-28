; starte den REPL ueber Rechtsklick auf Eclipseprojektordner Run as Clojure Application

; start unittest automation by evaluating the call to midje
(require 'midje.repl) ; https://github.com/marick/Midje https://github.com/marick/Midje/wiki/A-tutorial-introduction
(midje.repl/autotest)

; CCW changes the namespace for each evaluation to the namespace under which the code lies
; and after that switches back to the previous namespace just like pushd popd

; entferne vars von im REPL geladener Tests aus dem namespace

; Eclipse external webbrowser
; https://support.tasktop.com/hc/en-us/articles/200435116-How-do-I-configure-Eclipse-to-use-an-external-browser-

; network connection over proxy
; Eclipse proxy configuration: Eclipse Window Preferences General Network Connections Manual
; Maven proxy configuration:
; Set-Proxy-Envvar-FHB
; Set-Proxy-Envvar-FHB -off
