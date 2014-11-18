
package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategorySpecificSummary {

    @JsonProperty("costOptimizing")
    private org.dasein.cloud.aws.quotas.model.CostOptimizing CostOptimizing;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public org.dasein.cloud.aws.quotas.model.CostOptimizing getCostOptimizing() {
        return CostOptimizing;
    }

    public void setCostOptimizing(org.dasein.cloud.aws.quotas.model.CostOptimizing CostOptimizing) {
        this.CostOptimizing = CostOptimizing;
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
