package com.github.marc7806.process;

import java.io.IOException;
import java.util.List;

/**
 * Defines a interface for process executor
 */
public interface ProcessExecutor {
    Process execute(List<String> args) throws IOException;
}