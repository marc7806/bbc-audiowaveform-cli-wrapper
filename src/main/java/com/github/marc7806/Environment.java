package com.github.marc7806;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class that return environment variables
 *
 * @author Marc7806
 * @version 1.0
 */
public class Environment {
    private static final Logger log = LoggerFactory.getLogger(Environment.class);

    private static final String AWF_PATH_ENVIRONMENT_VARIABLE = "AWF_PATH";
    private final String awfPath;

    public Environment() {
        log.debug("Looking for 'AWF_PATH' environment variable");
        this.awfPath = checkNotNull(System.getProperty(AWF_PATH_ENVIRONMENT_VARIABLE), "Can not find bbc-audiowaveform executable path as system property. Please set property 'AWF_PATH'");
    }

    public String getAwfPath() {
        return this.awfPath;
    }
}
