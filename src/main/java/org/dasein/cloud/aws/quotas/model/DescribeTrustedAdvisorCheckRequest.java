package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DescribeTrustedAdvisorCheckRequest {

    @JsonProperty("checkId")
    private String checkId;
    @JsonProperty("language")
    private String language;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public DescribeTrustedAdvisorCheckRequest(String checkId, String language) {
        this.checkId = checkId;
        this.language = language;
    }

    /**
     *
     * @return
     * The checkId
     */
    @JsonProperty("checkId")
    public String getCheckId() {
        return checkId;
    }

    /**
     *
     * @param CheckId
     * The checkId
     */
    @JsonProperty("checkId")
    public void setCheckId(String CheckId) {
        this.checkId = CheckId;
    }

    /**
     *
     * @return
     * The language
     */
    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    /**
     *
     * @param Language
     * The language
     */
    @JsonProperty("language")
    public void setLanguage(String Language) {
        this.language = Language;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}