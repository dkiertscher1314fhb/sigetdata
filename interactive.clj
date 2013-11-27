; starte den REPL ueber Rechtsklick auf Eclipseprojektordner Run as Clojure Application

; start unittest automation by evaluating the call to midje
(require 'midje.repl) (midje.repl/autotest)

; CCW changes the namespace for each evaluation to the namespace under which the code lies
; and after that switches back to the previous namespace just like pushd popd
