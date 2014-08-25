package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "services"
})
public class CaseListServicesResponse {

    @JsonProperty("services")
    private List<CaseServiceResponse> caseServiceResponses = new ArrayList<CaseServiceResponse>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("services")
    public List<CaseServiceResponse> getCaseServiceResponses() {
        return caseServiceResponses;
    }

    @JsonProperty("services")
    public void setCaseServiceResponses(List<CaseServiceResponse> caseServiceResponses) {
        this.caseServiceResponses = caseServiceResponses;
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
