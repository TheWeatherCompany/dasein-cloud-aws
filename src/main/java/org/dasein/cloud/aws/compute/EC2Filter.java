package org.dasein.cloud.aws.compute;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * User: mgulimonov
 * Date: 29.05.2014
 */
public class EC2Filter {

    private int counter = 1;
    private Map<String, String> parameters;

    public EC2Filter() {
        parameters = new LinkedHashMap<String, String>();
    }

    public static EC2Filter singleFilter(String key, String value) {
        return new EC2Filter().addFilter(key, value);
    }

    public static EC2Filter singleParameterFilter(String key, String value) {
        return new EC2Filter().addParam(key, value);
    }

    public EC2Filter addParam(String key, String value) {
        checkNotEmpty(key, "Parameter key can't be empty");

        parameters.put(key, value);
        return this;
    }

    public EC2Filter addFilter(String key, String value) {
        checkNotEmpty(key, "Parameter key can't be empty");

        parameters.put("Filter." + counter + ".Name", key);
        parameters.put("Filter." + counter + ".Value.1", value);
        counter++;

        return this;
    }

    public EC2Filter addFilter(String key, Iterable<String> values) {
        parameters.put("Filter." + counter + ".Name", key);

        int i = 1;
        for (String value : values) {
            parameters.put("Filter." + counter + ".Value" + i, value);
            i++;
        }
        counter++;
        return this;
    }

    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    private void checkNotEmpty(String key, String message) {
        if (key == null || key.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
