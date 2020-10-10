package com.github.marc7806.process;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import javax.annotation.Nonnull;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.github.marc7806.Environment;
import com.github.marc7806.locator.AWFLocator;
import com.google.common.collect.ImmutableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Process executor class for bbc-audiowaveform
 * Executes bbc-audiowaveform process for a given set of arguments
 *
 * @author Marc7806
 * @version 1.0
 */
public class AWFExecutor implements ProcessExecutor {
    private static final Logger log = LoggerFactory.getLogger(AWFExecutor.class);

    private final AWFLocator _audioWaveformLocator;
    private File _workingDir;
    private boolean _redirectErrorStream = false;

    /**
     * Creates AWFExecutor for provided binary
     *
     * @param executablePath
     *         Path of bbc-audiowaveform binary
     */
    public AWFExecutor(String executablePath) {
        _audioWaveformLocator = new AWFLocator(executablePath);
    }

    public AWFExecutor() {
        _audioWaveformLocator = new AWFLocator(new Environment());
    }

    @Override
    public Process execute(@Nonnull List<String> args) throws IOException {
        checkNotNull(args, "Arguments must not be null");
        checkArgument(!args.isEmpty(), "Arguments are empty");

        ImmutableList<String> execArgs = new ImmutableList.Builder<String>()
                .add(_audioWaveformLocator.getExecutablePath())
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

    public String getVersion() throws IOException {
        List<String> args = new ArrayList<>();
        args.add("-v");
        Process process = this.execute(args);

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            return bufferedReader.readLine();
        }
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
