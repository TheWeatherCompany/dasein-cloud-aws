package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseCreateCaseResponse {

    @JsonProperty(value = "caseId")
    private String caseId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty(value = "caseId")
    public String getCaseId() {
        return caseId;
    }

    @JsonProperty(value = "caseId")
    public void setCaseId(String caseId) {
        this.caseId = caseId;
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
