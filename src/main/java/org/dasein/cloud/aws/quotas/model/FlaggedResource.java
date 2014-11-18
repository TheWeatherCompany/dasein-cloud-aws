
package org.dasein.cloud.aws.quotas.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class FlaggedResource {

    public static final int REGION_INDEX = 0;
    public static final int SERVICE_INDEX = 1;
    public static final int LIMIT_NAME_INDEX = 2;
    public static final int LIMIT_AMOUNT_INDEX = 3;
    public static final int CURRENT_USAGE_INDEX = 4;
    public static final int STATUS_INDEX = 5;

    @JsonProperty("isSuppressed")
    private String isSuppressed;
    @JsonProperty("metadata")
    private List<String> metadata = new ArrayList<String>();
    @JsonProperty("region")
    private String region;
    @JsonProperty("resourceId")
    private String resourceId;
    @JsonProperty("status")
    private String status;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public String serviceType() {
        return getMetadata().get(SERVICE_INDEX);
    }

    public String limitName() {
        return getMetadata().get(LIMIT_NAME_INDEX);
    }

    public int quota() {
        String value = getMetadata().get(LIMIT_AMOUNT_INDEX);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public int usage() {
        String value = getMetadata().get(CURRENT_USAGE_INDEX);
        return value != null ? Integer.parseInt(value) : 0;
    }

    public String getIsSuppressed() {
        return isSuppressed;
    }

    public void setIsSuppressed(String IsSuppressed) {
        this.isSuppressed = IsSuppressed;
    }

    public List<String> getMetadata() {
        return metadata;
    }

    public void setMetadata(List<String> Metadata) {
        this.metadata = Metadata;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String Region) {
        this.region = Region;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String ResourceId) {
        this.resourceId = ResourceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String Status) {
        this.status = Status;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override public String toString() {
        return "FlaggedResource{" +
                "region='" + region + '\'' +
                ", resourceId='" + resourceId + '\'' +
                ", metadata=" + metadata +
                '}';
    }
}
