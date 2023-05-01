all: run

SRCS := src/Bluck.java src/OTP.java
JARS := $(SRCS:src/%.java=out/%.jar)

clean:
	rm ${JARS}

out/Bluck.jar: out/parcs.jar src/Bluck.java src/DataEnc.java
	@javac -cp out/parcs.jar src/Bluck.java src/DataEnc.java
	@jar cf out/Bluck.jar -C src Bluck.class -C src DataEnc.class
	@rm -f src/Bluck.class src/DataEnc.class

out/OTP.jar: out/parcs.jar src/OTP.java src/DataEnc.java
	@javac -cp out/parcs.jar src/OTP.java src/DataEnc.java
	@jar cf out/OTP.jar -C src OTP.class -C src DataEnc.class
	@rm -f src/OTP.class src/DataEnc.class

build: ${JARS}

run: build
	@cd out && java -cp 'parcs.jar:Bluck.jar' Bluck