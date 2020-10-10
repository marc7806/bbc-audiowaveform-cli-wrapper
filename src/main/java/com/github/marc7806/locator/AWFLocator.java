package com.github.marc7806.locator;

import com.github.marc7806.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Locator class for BBC-Audiowaveform
 * Locates the bbc-audiowaveform executable and saves the executable path
 */
public class AWFLocator implements ExecutableLocator {
    private static final Logger log = LoggerFactory.getLogger(AWFLocator.class);

    private final String _executablePath;

    /**
     * Creates AWFLocator with bbc-audiowaveform binary file at specified location
     *
     * @param binaryPath Path of bbc-audiowaveform binary file
     */
    public AWFLocator(String binaryPath) {
        File executable = new File(binaryPath);
        checkArgument(executable.exists(), "Provided file with path '" + binaryPath + "' does not exist");
        setExecutablePermission(executable);
        _executablePath = binaryPath;
    }

    /**
     * Creates AWFLocator with bbc-audiowaveform binary file path from AWF_PATH environment variable
     */
    public AWFLocator(Environment env) {
        final File executable;

        try {
            File envExecFile = new File(env.getAwfPath());
            if (envExecFile.exists()) {
                executable = envExecFile;
            } else {
                throw new FileNotFoundException("The file from the provided environment file path does not exist. Have you provided the correct file path?");
            }
        } catch (Exception e) {
            log.error("Error trying to locate bbc-audiowaveform executable", e);
            throw new RuntimeException();
        }

        setExecutablePermission(executable);
        _executablePath = executable.getAbsolutePath();
    }

    private void setExecutablePermission(File execFile) {
        if (!execFile.canExecute()) {
            if (!execFile.setExecutable(true)) {
                log.error("Cannot set execute permission on bbc-audiowaveform executable.");
            }
        }
    }

    @Override
    public String getExecutablePath() {
        return _executablePath;
    }
}