package com.github.marc7806.wrapper;

import java.util.List;

/**
 * Interface for a command that defines arguments for command line executor
 *
 * @author Marc7806
 * @version 1.0
 */
public interface Command {
    /**
     * @return A list of string, where each string represents a command line argument
     */
    List<String> getArguments();
}
