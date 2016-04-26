sources = $(wildcard src/*.java)
classes = $(sources:.java=.class)

all: $(classes)

%.class : %.java
	javac -cp "lib/*;src" $<
	
clean :
	rm -f src/*.class
	rm -f App.jar
	
jar : all
	jar cvfme App.jar manifest Application -C src .
	java -jar App.jar