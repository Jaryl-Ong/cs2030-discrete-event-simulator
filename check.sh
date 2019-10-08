#!/bin/bash
javac *.java
for f in Test/*.in; do bn=$(basename -- "$f"); fn="${bn%.*}"; echo $fn; java -ea Main < Test/$fn.in | diff - Test/$fn.out; done