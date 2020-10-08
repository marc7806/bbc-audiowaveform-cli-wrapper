package com.github.marc7806.locator;

import com.github.marc7806.Version;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class AWFLocatorTest {
    @Test
    public void shouldThrowExceptionWhenExecutableNotAvailable() {
        //given
        Version version = Mockito.mock(Version.class);
        when(version.getVersion()).thenReturn("0.0.1");

        //then
        assertThrows(RuntimeException.class, () -> new AWFLocator(version));
    }

    @Test
    public void shouldGetExecutablePath() {
        //given
        Version version = new Version();
        ExecutableLocator locator = new AWFLocator(version);

        //when
        String execPath = locator.getExecutablePath();

        //then
        assertNotNull(execPath);
        assertThat(execPath, containsString("audiowaveform"));
    }
}