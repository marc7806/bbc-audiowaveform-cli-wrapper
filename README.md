# Audio Waveform Summary Generator Java
A Java wrapper around the **[bbc-audiowaveform](https://github.com/bbc/audiowaveform)** command line tool. 

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
mvn clean install -DAWF_PATH="path/to/audiowaveform/executable"
```
With this set, you can create a new BBCAudioWaveform instance like this:
```java
BBCAudioWaveForm bbcAudioWaveform = new BBCAudioWaveform(); // no need for path, because binary path is set via system property;
```

## Authors
This java-wrapper was written by [Marc7806](https://github.com/marc7806/)