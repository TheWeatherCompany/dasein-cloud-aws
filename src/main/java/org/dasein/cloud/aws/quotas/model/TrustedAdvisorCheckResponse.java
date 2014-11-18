
package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrustedAdvisorCheckResponse {

    @JsonProperty("result")
    private TrustedAdvisorCheckResult trustedAdvisorCheckResult;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public TrustedAdvisorCheckResult getTrustedAdvisorCheckResult() {
        return trustedAdvisorCheckResult;
    }

    public void setTrustedAdvisorCheckResult(TrustedAdvisorCheckResult TrustedAdvisorCheckResult) {
        this.trustedAdvisorCheckResult = TrustedAdvisorCheckResult;
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
