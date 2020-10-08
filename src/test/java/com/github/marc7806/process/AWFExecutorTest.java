package com.github.marc7806.process;

import com.github.marc7806.Version;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

class AWFExecutorTest {

    @Test
    public void shouldExecuteVersionCheck() throws IOException {
        //given
        Version version = new Version();
        AWFExecutor executor = new AWFExecutor();

        //when
        List<String> args = new ArrayList<>();
        args.add("-v");
        Process process = executor.execute(args);

        //then
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String versionLine = bufferedReader.readLine();

            assertThat(versionLine, containsString(version.getVersion()));
        }
    }
}