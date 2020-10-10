# Audio Waveform Summary Generator Java
A Java wrapper around the **[bbc-audiowaveform](https://github.com/bbc/audiowaveform)** command line tool. 

### Included binaries
Used bbc-audiowaveform version: ```1.4.2```  
The Java wrapper currently contains bbc-audiowaveform executables for following OS:
* Mac-OS x86_64

## Usage
### Generate waveform data in JSON format
```java
BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();

AWFCommand command = AWFCommand.builder()
        .setInput(new File("input.mp3")) // .mp3, .wav, .flac, .ogg, .oga, or .dat
        .setOutput(new File("output.json")) // .wav, .dat, .json, or .png
        .setBits(AWFBit.EIGHT) // number of data bits to use for output waveform data points
        .addZoom(256) // number of input samples to use to generate each output waveform data point
        .build();

boolean success = bbcAudioWaveForm.run(command); // returns true if success, otherwise false
```

### Create PNG image from an MP3 file
```java
BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();

AWFCommand command = AWFCommand.builder()
        .setInput(new File("input.mp3"))
        .setOutput(new File("output.png"))
        .splitChannels() // render for each channel
        .setWidth(1920) // set 1920 pixel as image width
        .setHeight(1080) // set 1000 pixel as image height
        .setAmplitudeScale("auto") // scales the waveform to the maximum height
        .setColorSchema(AWFColorSchema.AUDITION) // set waveform color schema (default is audacity)
        .build();

boolean success = bbcAudioWaveForm.run(command);
```

## Authors
This java-wrapper was written by [Marc7806](https://github.com/marc7806/)