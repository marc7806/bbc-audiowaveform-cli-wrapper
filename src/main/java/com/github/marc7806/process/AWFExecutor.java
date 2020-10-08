package com.github.marc7806.process;

import com.github.marc7806.locator.AWFLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class AWFExecutor implements ProcessExecutor {
    private static final Logger log = LoggerFactory.getLogger(AWFExecutor.class);
    private final AWFLocator _audioWaveFormLocator;
    private File _workingDir;
    private boolean _redirectErrorStream = false;

    public AWFExecutor() {
        _audioWaveFormLocator = new AWFLocator();
    }

    @Override
    public Process execute(List<String> args) throws IOException {
        checkNotNull(args, "Arguments must not be null");
        checkArgument(!args.isEmpty(), "Arguments are empty");

        args.add(0, _audioWaveFormLocator.getExecutablePath());
        ProcessBuilder processBuilder = new ProcessBuilder(args);
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