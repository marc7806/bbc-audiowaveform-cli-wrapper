package com.github.marc7806.process;

import com.github.marc7806.Version;
import com.github.marc7806.locator.AWFLocator;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Process executor class for bbc-audiowaveform
 * Executes bbc-audiowaveform process for a given set of arguments
 */
public class AWFExecutor implements ProcessExecutor {
    private static final Logger log = LoggerFactory.getLogger(AWFExecutor.class);

    private final AWFLocator _audioWaveFormLocator;
    private File _workingDir;
    private boolean _redirectErrorStream = false;

    public AWFExecutor() {
        _audioWaveFormLocator = new AWFLocator(new Version());
    }

    @Override
    public Process execute(List<String> args) throws IOException {
        checkNotNull(args, "Arguments must not be null");
        checkArgument(!args.isEmpty(), "Arguments are empty");

        ImmutableList<String> execArgs = new ImmutableList.Builder<String>()
                .add(_audioWaveFormLocator.getExecutablePath())
                .addAll(args)
                .build();
        ProcessBuilder processBuilder = new ProcessBuilder(execArgs);
        processBuilder.redirectErrorStream(_redirectErrorStream);

        if (_workingDir != null) {
            processBuilder.directory(_workingDir);
        }

        log.info("About to execute: {}", String.join(" ", processBuilder.command()));
        return processBuilder.start();
    }

    public AWFExecutor setWorkingDirectory(File workingDir) {
        _workingDir = workingDir;
        return this;
    }

    public AWFExecutor redirectErrorStream(boolean redirectErrorStream) {
        _redirectErrorStream = redirectErrorStream;
        return this;
    }
}