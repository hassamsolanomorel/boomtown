#!/bin/bash
echo 'Compiling'
javac -cp ./src/gson-2.8.5.jar:./src/json-simple-1.1.1.jar ./src/*.java
echo 'Compiled!'
java -cp ./src/gson-2.8.5.jar:./src/json-simple-1.1.1.jar:./src BoomTown_Main