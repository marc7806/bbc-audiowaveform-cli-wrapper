package com.github.marc7806.locator;

import com.github.marc7806.Version;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AWFLocatorTest {
    @Test
    public void shouldGetExecutablePath() {
        //given
        ExecutableLocator locator = new AWFLocator();

        //when
        String execPath = locator.getExecutablePath();

        //then
        assertNotNull(execPath);
        assertThat(execPath, containsString("audiowaveform"));
    }

    @Test
    public void shouldThrowExceptionWhenExecutableNotAvailable() {
        //given
        Mockito.mockStatic(Version.class);
        Mockito.when(Version.getVersion()).thenReturn("0.0.1");

        //then
        assertThrows(RuntimeException.class, AWFLocator::new);
    }
}