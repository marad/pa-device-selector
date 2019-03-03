.PHONY: clean repl

rwildcard=$(wildcard $1$2) $(foreach d,$(wildcard $1*),$(call rwildcard,$d/,$2))
SOURCES := $(call rwildcard,src/,*.clj)
MAIN_NAMESPACE = sas
NATIVE_NAME = select-audio-sink

native: $(SOURCES)
	clj -R:cambada -m cambada.native-image --graalvm-home ~/apps/graalvm-ce-1.0.0-rc10 -m $(MAIN_NAMESPACE).core --image-name $(NATIVE_NAME) 

install: native
	cp target/$(NATIVE_NAME) ~/bin/

uberjar : $(SOURCES)
	clj -R:cambada -m cambada.uberjar

repl:
	clj -A:cider-clj

clean:
	rm -rf target
	rm -rf classes
