
package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class TrustedAdvisorCheckResult {

    @JsonProperty("categorySpecificSummary")
    private org.dasein.cloud.aws.quotas.model.CategorySpecificSummary categorySpecificSummary;
    @JsonProperty("checkId")
    private String checkId;
    @JsonProperty("flaggedResources")
    private List<FlaggedResource> flaggedResources = new ArrayList<FlaggedResource>();
    @JsonProperty("resourcesSummary")
    private org.dasein.cloud.aws.quotas.model.ResourcesSummary resourcesSummary;
    @JsonProperty("status")
    private String status;
    @JsonProperty("timestamp")
    private String timestamp;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public org.dasein.cloud.aws.quotas.model.CategorySpecificSummary getCategorySpecificSummary() {
        return categorySpecificSummary;
    }

    public void setCategorySpecificSummary(org.dasein.cloud.aws.quotas.model.CategorySpecificSummary CategorySpecificSummary) {
        this.categorySpecificSummary = CategorySpecificSummary;
    }

    public String getCheckId() {
        return checkId;
    }

    public void setCheckId(String CheckId) {
        this.checkId = CheckId;
    }

    public List<FlaggedResource> getFlaggedResources() {
        return flaggedResources;
    }

    public void setFlaggedResources(List<FlaggedResource> FlaggedResources) {
        this.flaggedResources = FlaggedResources;
    }

    public org.dasein.cloud.aws.quotas.model.ResourcesSummary getResourcesSummary() {
        return resourcesSummary;
    }

    public void setResourcesSummary(org.dasein.cloud.aws.quotas.model.ResourcesSummary ResourcesSummary) {
        this.resourcesSummary = ResourcesSummary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String Timestamp) {
        this.timestamp = Timestamp;
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
