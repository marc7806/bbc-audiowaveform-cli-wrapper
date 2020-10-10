package com.github.marc7806.locator;

/**
 * ExecutableLocator interface.
 *
 * @author Marc7806
 * @version 1.0
 */
public interface ExecutableLocator {
    /**
     * Should return the correct bbc-audiowaveform executable for current operating system
     *
     * @return The path of bbc-audiowaveform executable
     */
    String getExecutablePath();
}
