package com.github.marc7806.process;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;

import org.junit.jupiter.api.Test;

class AWFExecutorTest {
    @Test
    void shouldExecuteVersionCheck() throws IOException {
        //given
        AWFExecutor executor = new AWFExecutor();

        //when
        String version = executor.getVersion();

        // then
        assertTrue(version.contains("AudioWaveform"));
    }
}
