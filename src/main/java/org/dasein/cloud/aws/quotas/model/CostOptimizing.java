
package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CostOptimizing {

    @JsonProperty("estimatedMonthlySavings")
    private String estimatedMonthlySavings;
    @JsonProperty("estimatedPercentMonthlySavings")
    private String estimatedPercentMonthlySavings;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getEstimatedMonthlySavings() {
        return estimatedMonthlySavings;
    }

    public void setEstimatedMonthlySavings(String EstimatedMonthlySavings) {
        this.estimatedMonthlySavings = EstimatedMonthlySavings;
    }

    public String getEstimatedPercentMonthlySavings() {
        return estimatedPercentMonthlySavings;
    }

    public void setEstimatedPercentMonthlySavings(String EstimatedPercentMonthlySavings) {
        this.estimatedPercentMonthlySavings = EstimatedPercentMonthlySavings;
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
