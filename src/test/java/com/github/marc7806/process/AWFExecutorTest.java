package com.github.marc7806.process;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AWFExecutorTest {
    @Test
    public void shouldExecuteVersionCheck() throws IOException {
        //given
        AWFExecutor executor = new AWFExecutor();

        //when
        String version = executor.getVersion();

        // then
        assertTrue(version.contains("AudioWaveform"));
    }
}