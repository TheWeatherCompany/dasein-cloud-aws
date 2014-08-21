
package org.dasein.cloud.aws.resource.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "communications",
        "nextToken"
})
public class RecentCommunications {

    @JsonProperty("communications")
    private List<Communication> communications = new ArrayList<Communication>();
    @JsonProperty(value = "nextToken")
    String nextToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("communications")
    public List<Communication> getCommunications() {
        return communications;
    }

    @JsonProperty("communications")
    public void setCommunications(List<Communication> communications) {
        this.communications = communications;
    }

    @JsonProperty(value = "nextToken")
    public String getNextToken() {
        return nextToken;
    }

    @JsonProperty(value = "nextToken")
    public void setNextToken(String nextToken) {
        this.nextToken = nextToken;
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
