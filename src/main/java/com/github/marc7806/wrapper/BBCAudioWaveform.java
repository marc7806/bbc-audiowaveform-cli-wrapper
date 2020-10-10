package com.github.marc7806.wrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.github.marc7806.process.AWFExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Wrapper for BBCAudioWaveform cli tool
 *
 * @author Marc7806
 * @version 1.0
 */
public class BBCAudioWaveform {
    private static final Logger log = LoggerFactory.getLogger(BBCAudioWaveform.class);

    private final AWFExecutor executor;

    /**
     * Constructor that takes binary path
     *
     * @param pathToBinary
     *         Path to bbc-audiowaveform executable
     */
    public BBCAudioWaveform(String pathToBinary) {
        this.executor = new AWFExecutor(pathToBinary)
                .redirectErrorStream(true);
    }

    /**
     * Reads executable path from system property
     */
    public BBCAudioWaveform() {
        this.executor = new AWFExecutor()
                .redirectErrorStream(true);
    }

    /**
     * Runs a new bbc-audiowaveform process for a given command
     *
     * @param command
     *         The command that contains the arguments for running bbc-audiowaveform process
     * @return boolean that indicates process success. True if successfully run, otherwise false.
     */
    public boolean run(AWFCommand command) {
        log.info("Start executing bbc-audiowaveform command");
        try {
            final Process process = this.executor.execute(command.getArguments());
            int exitCode = process.waitFor();

            // read and log process output
            try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                bufferedReader.lines()
                        .forEach(log::info);
            }

            if (exitCode == 0) {
                log.info("Finished bbc-audiowaveform command execution");
                return true;
            } else {
                log.error("Process did not end successfully with exit code {}", exitCode);
            }
        } catch (IOException e) {
            log.error("Error executing AWFCommand", e);
        } catch (InterruptedException e) {
            log.warn("AWF Process execution interrupted either before or during the activity", e);
            Thread.currentThread().interrupt();
        }
        return false;
    }
}
