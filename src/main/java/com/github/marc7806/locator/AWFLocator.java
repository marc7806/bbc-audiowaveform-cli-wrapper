package com.github.marc7806.locator;

import static com.google.common.base.Preconditions.checkArgument;
import java.io.File;
import java.io.FileNotFoundException;

import com.github.marc7806.Environment;
import com.github.marc7806.exception.ExecutableLocatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Locator class for BBC-Audiowaveform
 * Locates the bbc-audiowaveform executable and saves the executable path
 *
 * @author Marc7806
 * @version 1.0
 */
public class AWFLocator implements ExecutableLocator {
    private static final Logger log = LoggerFactory.getLogger(AWFLocator.class);

    private final String executablePath;

    /**
     * Creates AWFLocator with bbc-audiowaveform binary file at specified location
     *
     * @param binaryPath
     *         Path of bbc-audiowaveform binary file
     */
    public AWFLocator(String binaryPath) {
        File executable = new File(binaryPath);
        checkArgument(executable.exists(), "Provided file with path '" + binaryPath + "' does not exist");
        setExecutablePermission(executable);
        this.executablePath = binaryPath;
    }

    /**
     * Creates AWFLocator with bbc-audiowaveform binary file path from AWF_PATH environment variable
     *
     * @param env
     *         a {@link Environment} object.
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
            throw new ExecutableLocatorException("Error trying to locate bbc-audiowaveform executable", e);
        }

        setExecutablePermission(executable);
        this.executablePath = executable.getAbsolutePath();
    }

    private void setExecutablePermission(File execFile) {
        if (!execFile.canExecute() && !execFile.setExecutable(true)) {
            log.error("Cannot set execute permission on bbc-audiowaveform executable.");
        }
    }

    @Override
    public String getExecutablePath() {
        return this.executablePath;
    }
}
