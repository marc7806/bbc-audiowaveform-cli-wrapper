package com.github.marc7806.locator;

public interface ExecutableLocator {
    /**
     * Should return the correct bbc-audiowaveform executable for current operating system
     *
     * @return The path of bbc-audiowaveform executable
     */
    String getExecutablePath();
}