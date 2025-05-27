
in: /path/to/oop-ex-toy-robot-group$
run this in terminal first to compile:

mvn compile

or:
mvn compile exec:java
to compile then run.

or //same thing

mvn compile exec:java -Dexec.mainClass="za.co.wethinkcode.robots.server.Server"

Then you can just run:

mvn exec:java

to execute the program normally.

Then to run client:

java -cp target/classes za.co.wethinkcode.robots.server.Client
