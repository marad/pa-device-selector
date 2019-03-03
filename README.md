# pa-device-selector

This is a simple program that shows you a dmenu with available PulseAudio sinks
and switches to the selected one.

## Binaries

You can download the binary relaease here: https://github.com/marad/pa-device-selector/releases/tag/v0.1

## Runtime requirements

This tool requires your system to have `pacmd` and `dmenu` tools on your PATH.

## Build

To build the native thing you need to have all required things set up (see below).
And set the `--graalvm-home` switch inside the `Makefile`. Then do:

```bash
make native
```

And when the work is done you'll have your binary at `target/select-audio-sink`.

### Requirements

* graalvm-ce 1.0.0-rc10 (this is what I've used, may work with newer)
* clojure tools (clj)




