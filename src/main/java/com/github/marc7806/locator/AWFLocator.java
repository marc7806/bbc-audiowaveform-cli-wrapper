package com.github.marc7806.locator;

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

import static com.github.marc7806.Version.getVersion;

public class AWFLocator implements ExecutableLocator {
    private static final Logger log = LoggerFactory.getLogger(AWFLocator.class);
    private static final String TMP_BASE_DIR = "bbc-audiowaveform-java/" + getVersion() + "/";
    private static final String RESOURCES_BASE_DIR = "bbc-audiowaveform/" + getVersion() + "/";
    private static String _executablePath;

    public AWFLocator() {
        File baseDir = initializeTempDir();
        String execName = determineExecutableName();
        File execFile = new File(baseDir, execName);

        try {
            if (execFile.exists()) {
                log.debug("Executable exists in '{}'", execFile.getAbsolutePath());
            } else {
                log.debug("Copy executable to '{}'", execFile.getAbsolutePath());
                copyFile(RESOURCES_BASE_DIR + execName, execFile);
            }
        } catch (ExecutableLocatorException e) {
            log.error("Error trying to locate bbc-audiowaveform executable", e);
            throw new RuntimeException();
        }
        setExecutablePermission(execFile);
        _executablePath = execFile.getAbsolutePath();
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
        File dirFolder = new File(System.getProperty("java.io.tmpdir"), TMP_BASE_DIR);
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