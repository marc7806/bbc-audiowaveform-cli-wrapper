# Audio Waveform Summary Generator Java
A Java wrapper around the **[bbc-audiowaveform](https://github.com/bbc/audiowaveform)** command line tool. 

![Java](https://img.shields.io/badge/Java-11+-brightgreen.svg)
[![Issues](https://img.shields.io/github/issues/marc7806/bbc-audiowaveform-cli-wrapper)](https://github.com/marc7806/bbc-audiowaveform-cli-wrapper/issues)
[![CircleCI](https://circleci.com/gh/marc7806/bbc-audiowaveform-cli-wrapper/tree/main.svg?style=shield)](https://circleci.com/gh/marc7806/bbc-audiowaveform-cli-wrapper/tree/main)
[![Coverage Status](https://img.shields.io/sonar/coverage/sonarcloud-marc7806?server=https%3A%2F%2Fsonarcloud.io)](https://sonarcloud.io/dashboard?id=sonarcloud-marc7806)
[![Version](https://img.shields.io/maven-central/v/com.github.marc7806/audiowaveform)](https://search.maven.org/artifact/com.github.marc7806/audiowaveform/1.1/jar)

## Install
Maven:
```xml
<dependency>
  <groupId>com.github.marc7806</groupId>
  <artifactId>audiowaveform</artifactId>
  <version>1.2</version>
</dependency>
```

## Usage
### Generate waveform data in JSON format
```java
BBCAudioWaveForm bbcAudioWaveform = new BBCAudioWaveform("path/to/audiowaveform/executable");

AWFCommand command = AWFCommand.builder()
        .setInput(new File("input.mp3")) // .mp3, .wav, .flac, .ogg, .oga, or .dat
        .setOutput(new File("output.json")) // .wav, .dat, .json, or .png
        .setBits(AWFBit.EIGHT) // number of data bits to use for output waveform data points
        .addZoom(256) // number of input samples to use to generate each output waveform data point
        .build();

boolean success = bbcAudioWaveform.run(command); // returns true if success, otherwise false
```

### Create PNG image from an MP3 file
```java
BBCAudioWaveForm bbcAudioWaveform = new BBCAudioWaveform("path/to/audiowaveform/executable");

AWFCommand command = AWFCommand.builder()
        .setInput(new File("input.mp3"))
        .setOutput(new File("output.png"))
        .splitChannels() // render for each channel
        .setWidth(1920) // set 1920 pixel as image width
        .setHeight(1080) // set 1000 pixel as image height
        .setAmplitudeScale("auto") // scales the waveform to the maximum height
        .setColorSchema(AWFColorSchema.AUDITION) // set waveform color schema (default is audacity)
        .build();

boolean success = bbcAudioWaveform.run(command);
```

## Setup bbc-audiowaveform executable
To install the BBC-AudioWaveform cli-tool please follow the [official documentation](https://github.com/bbc/audiowaveform).

Currently, to get the wrapper working, there are multiple options to set the location of the bbc-audiowaveform binary.

### Code
You can provide the path to your executable directly when declaring a new instance of BBCAudioWaveform
```java
BBCAudioWaveForm bbcAudioWaveform = new BBCAudioWaveform("path/to/audiowaveform/executable");
```

### System property
You can also set the path to your bbc-audiowaveform binary via the ```AWF_PATH``` system property:
```
$ mvn clean install -DAWF_PATH="path/to/audiowaveform/executable"
```
With this set, you can create a new BBCAudioWaveform instance like this:
```java
BBCAudioWaveForm bbcAudioWaveform = new BBCAudioWaveform(); // no need for path, because binary path is set via system property;
```

## Authors
This java-wrapper was written by [Marc7806](https://github.com/marc7806/)