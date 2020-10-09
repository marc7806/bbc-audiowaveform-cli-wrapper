package com.github.marc7806.wrapper;

import com.google.common.io.Files;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.google.common.hash.Hashing.sha256;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BBCAudioWaveFormTest {
    @Test
    public void shouldSuccessfullyRunJSON_B_8_Sample() throws IOException {
        //given
        File input = new File(getClass().getClassLoader().getResource("audio/sample_01.mp3").getFile());
        File output = File.createTempFile("awf-sample_01_summary", ".json");
        output.deleteOnExit();
        BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();
        AWFCommand command = AWFCommand.builder()
                .setInput(input)
                .setOutput(output)
                .setBits(AWFBit.EIGHT)
                .build();

        //when
        boolean success = bbcAudioWaveForm.run(command);

        //then
        File expected = new File(getClass().getClassLoader().getResource("json/sample_01_B_8.json").getFile());
        assertTrue(success);
        assertEquals(Files.asByteSource(expected).hash(sha256()), Files.asByteSource(output).hash(sha256()));
    }

    @Test
    public void shouldSuccessfullyRunJSON_B_8_Z_2048_Sample() throws IOException {
        //given
        File input = new File(getClass().getClassLoader().getResource("audio/sample_01.mp3").getFile());
        File output = File.createTempFile("awf-sample_01_summary", ".json");
        output.deleteOnExit();
        BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();
        AWFCommand command = AWFCommand.builder()
                .setInput(input)
                .setOutput(output)
                .setBits(AWFBit.EIGHT)
                .addZoom(2048)
                .build();

        //when
        boolean success = bbcAudioWaveForm.run(command);

        //then
        File expected = new File(getClass().getClassLoader().getResource("json/sample_01_B_8_Z_2048.json").getFile());
        assertTrue(success);
        assertEquals(Files.asByteSource(expected).hash(sha256()), Files.asByteSource(output).hash(sha256()));
    }

    @Test
    public void shouldSuccessfullyRunJSON_B_16_Sample() throws IOException {
        //given
        File input = new File(getClass().getClassLoader().getResource("audio/sample_01.mp3").getFile());
        File output = File.createTempFile("awf-sample_01_summary", ".json");
        output.deleteOnExit();
        BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();
        AWFCommand command = AWFCommand.builder()
                .setInput(input)
                .setOutput(output)
                .setBits(AWFBit.SIXTEEN)
                .build();

        //when
        boolean success = bbcAudioWaveForm.run(command);

        //then
        File expected = new File(getClass().getClassLoader().getResource("json/sample_01_B_16.json").getFile());
        assertTrue(success);
        assertEquals(Files.asByteSource(expected).hash(sha256()), Files.asByteSource(output).hash(sha256()));
    }

    @Test
    public void shouldSuccessfullyRunJSON_B_16_Z_2048_Sample() throws IOException {
        //given
        File input = new File(getClass().getClassLoader().getResource("audio/sample_01.mp3").getFile());
        File output = File.createTempFile("awf-sample_01_summary", ".json");
        output.deleteOnExit();
        BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();
        AWFCommand command = AWFCommand.builder()
                .setInput(input)
                .setOutput(output)
                .setBits(AWFBit.SIXTEEN)
                .addZoom(2048)
                .build();

        //when
        boolean success = bbcAudioWaveForm.run(command);

        //then
        File expected = new File(getClass().getClassLoader().getResource("json/sample_01_B_16_Z_2048.json").getFile());
        assertTrue(success);
        assertEquals(Files.asByteSource(expected).hash(sha256()), Files.asByteSource(output).hash(sha256()));
    }

    @Test
    public void shouldSuccessfullyRunPNG_SPLIT_W_1920_H_1080_AUTO_AUDITION() throws IOException {
        //given
        File input = new File(getClass().getClassLoader().getResource("audio/sample_01.mp3").getFile());
        File output = File.createTempFile("awf-sample_01_summary", ".png");
        output.deleteOnExit();
        BBCAudioWaveForm bbcAudioWaveForm = new BBCAudioWaveForm();
        AWFCommand command = AWFCommand.builder()
                .setInput(input)
                .setOutput(output)
                .splitChannels()
                .setWidth(1920)
                .setHeight(1080)
                .setAmplitudeScale("auto")
                .setColorSchema(AWFColorSchema.AUDITION)
                .build();

        //when
        boolean success = bbcAudioWaveForm.run(command);

        //then
        File expected = new File(getClass().getClassLoader().getResource("image/sample_01_SPLIT_W_1920_H_1080_AUTO_AUDITION.png").getFile());
        assertTrue(success);
        assertEquals(Files.asByteSource(expected).hash(sha256()), Files.asByteSource(output).hash(sha256()));
    }
}