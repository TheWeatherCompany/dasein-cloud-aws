package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * User: mgulimonov
 * Date: 17.11.2014
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
public class TrustedAdvisorChecksRequest {

    @JsonProperty("language")
    private String language;

    public TrustedAdvisorChecksRequest(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
