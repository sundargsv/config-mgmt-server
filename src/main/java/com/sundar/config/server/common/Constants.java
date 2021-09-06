package com.sundar.config.server.common;

import java.io.File;

public class Constants {

    public static final String API_BASE_CONTEXT_PATH = "/config";

    public static final String STATUS_SUCCESS = "SUCCESS";
    public static final String STATUS_FAILURE = "FAILURE";

    // the below is the Git cloud repo for config
    public static File GIT_LOCAL_DIR;

    // the below is the local directory for config
    public static File LOCAL_DIR;

    // the below is the application config property file extension
    public static String FILE_EXTENSION = ".yaml";

    public class Header {
        public static final String SAMPLE = "sample";
    }

    public class Error {
        public static final String FIELD_MISSING = "Field {} is required";
        public static final String UNKNOWN_FIELD = "Field {} is should be cm/inch";
    }

    public class Message {
        public static final String SUCCESSFULLY_SAVED_SAMPLE_DATA_MSG = "Sample info saved successfully.";
    }
}
