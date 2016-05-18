CP		= "lib/*;src"
NAME	= App
sources = $(wildcard src/*.java)
classes = $(sources:.java=.class)

all : $(classes)

j : CP = "lib/*:src"
j : all

%.class : %.java
	javac -cp $(CP) -proc:none $<
	
clean :
	rm -f src/*.class
	rm -f $(NAME).jar
	
jar : $(NAME).jar

$(NAME).jar : all
	jar cvfme $(NAME).jar manifest Application -C src .
	

run : $(NAME).jar
	java -jar $(NAME).jar

doc :
	javadoc -private -d doc -cp "lib/*" src/*.java
