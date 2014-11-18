
package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourcesSummary {

    @JsonProperty("resourcesFlagged")
    private String resourcesFlagged;
    @JsonProperty("resourcesIgnored")
    private String resourcesIgnored;
    @JsonProperty("resourcesProcessed")
    private String resourcesProcessed;
    @JsonProperty("resourcesSuppressed")
    private String resourcesSuppressed;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String getResourcesFlagged() {
        return resourcesFlagged;
    }

    public void setResourcesFlagged(String ResourcesFlagged) {
        this.resourcesFlagged = ResourcesFlagged;
    }

    public String getResourcesIgnored() {
        return resourcesIgnored;
    }

    public void setResourcesIgnored(String ResourcesIgnored) {
        this.resourcesIgnored = ResourcesIgnored;
    }

    public String getResourcesProcessed() {
        return resourcesProcessed;
    }

    public void setResourcesProcessed(String ResourcesProcessed) {
        this.resourcesProcessed = ResourcesProcessed;
    }

    public String getResourcesSuppressed() {
        return resourcesSuppressed;
    }

    public void setResourcesSuppressed(String ResourcesSuppressed) {
        this.resourcesSuppressed = ResourcesSuppressed;
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
