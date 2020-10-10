package com.github.marc7806.exception;

/**
 * Exception that gets thrown when no executable is found
 */
public class ExecutableLocatorException extends RuntimeException {
    public ExecutableLocatorException(String message) {
        super(message);
    }

    public ExecutableLocatorException(String message, Throwable cause) {
        super(message, cause);
    }
}
