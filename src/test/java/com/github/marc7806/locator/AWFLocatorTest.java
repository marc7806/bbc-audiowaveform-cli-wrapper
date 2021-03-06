package com.github.marc7806.locator;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import java.io.File;
import java.io.IOException;

import com.github.marc7806.Environment;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class AWFLocatorTest {
    @Test
    void shouldThrowExceptionWhenExecutableNotAvailable() {
        assertThrows(RuntimeException.class, () -> {
            new AWFLocator("/demo/test");
        });
    }

    @Test
    void shouldGetAWFLocatorWithValidBinaryPath() throws IOException {
        File tmpBinary = File.createTempFile("audiowaveform", "");
        tmpBinary.deleteOnExit();
        AWFLocator locator = new AWFLocator(tmpBinary.getAbsolutePath());
        assertEquals(tmpBinary.getAbsolutePath(), locator.getExecutablePath());
    }

    @Test
    void shouldGetExecutablePathWithAWFEnvironment() throws IOException {
        //given
        File tmpBinary = File.createTempFile("audiowaveform", "");
        tmpBinary.deleteOnExit();

        Environment env = Mockito.mock(Environment.class);
        when(env.getAwfPath()).thenReturn(tmpBinary.getAbsolutePath());
        ExecutableLocator locator = new AWFLocator(env);

        //when
        String execPath = locator.getExecutablePath();

        //then
        assertNotNull(execPath);
        assertThat(execPath, containsString("audiowaveform"));
    }

    @Test
    void shouldThrowExceptionWhenNoValidEnvironmentPath() {
        //given
        Environment env = Mockito.mock(Environment.class);
        when(env.getAwfPath()).thenReturn("/demo/test");

        assertThrows(RuntimeException.class, () -> {
            new AWFLocator(env);
        });
    }
}
