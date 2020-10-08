package com.github.marc7806.locator;

import com.github.marc7806.Version;
import com.github.marc7806.exception.ExecutableLocatorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * Locator class for BBC-Audiowaveform
 * Locates the bbc-audiowaveform executable and saves the executable path
 */
public class AWFLocator implements ExecutableLocator {
    private static final Logger log = LoggerFactory.getLogger(AWFLocator.class);

    private final Version _version;
    private final String _executablePath;
    private String _tempBaseDir;
    private String _resourcesBaseDir;

    public AWFLocator(Version version) {
        _version = version;
        buildDirectoryPaths();
        File baseDir = initializeTempDir();
        String execName = determineExecutableName();
        File execFile = new File(baseDir, execName);

        try {
            if (execFile.exists()) {
                log.debug("Executable exists in '{}'", execFile.getAbsolutePath());
            } else {
                log.debug("Copy executable to '{}'", execFile.getAbsolutePath());
                copyFile(_resourcesBaseDir + execName, execFile);
            }
        } catch (ExecutableLocatorException e) {
            log.error("Error trying to locate bbc-audiowaveform executable", e);
            throw new RuntimeException();
        }
        setExecutablePermission(execFile);
        _executablePath = execFile.getAbsolutePath();
    }

    private void buildDirectoryPaths() {
        _tempBaseDir = "bbc-audiowaveform-java/" + _version.getVersion() + "/";
        _resourcesBaseDir = "bbc-audiowaveform/" + _version.getVersion() + "/";
    }

    private void setExecutablePermission(File execFile) {
        if (!execFile.canExecute()) {
            if (!execFile.setExecutable(true)) {
                log.error("Cannot set execute permission on bbc-audiowaveform executable.");
            }
        }
    }

    private void copyFile(String sourcePath, File targetFile) throws ExecutableLocatorException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(sourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("No valid bbc-audiowaveform executable on resources classpath. Maybe your operating system is not supported.");
            }
            Files.copy(is, Paths.get(targetFile.getAbsolutePath()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new ExecutableLocatorException("Could not <read|write> bbc-audiowaveform executable file <from resources|to tmp> folder", e);
        }
    }

    private String determineExecutableName() {
        String os = System.getProperty("os.name").toLowerCase();
        String arch = System.getProperty("os.arch");
        log.debug("Current operating system is {} - {}", os, arch);

        String execSuffix = os.contains("windows") ? ".exe" : (os.contains("mac") ? "-osx" : "");
        StringBuilder sb = new StringBuilder();
        sb.append("audiowaveform-");
        sb.append(arch);
        sb.append(execSuffix);
        return sb.toString();
    }

    private File initializeTempDir() {
        File dirFolder = new File(System.getProperty("java.io.tmpdir"), _tempBaseDir);
        if (dirFolder.exists()) {
            log.debug("bbc-audiowaveform temp folder already exists in '{}'", dirFolder.getAbsolutePath());
        } else {
            log.debug(
                    "Creating bbc-audiowaveform temp folder to place executables in '{}'", dirFolder.getAbsolutePath());
            dirFolder.mkdirs();
        }
        return dirFolder;
    }

    @Override
    public String getExecutablePath() {
        return _executablePath;
    }
}