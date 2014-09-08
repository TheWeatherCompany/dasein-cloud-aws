package org.dasein.cloud.aws.platform.support.model;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {"cases", "nextToken"} )
public class CaseDetails {

    @JsonProperty( "cases" )
    private List<Case> cases = new ArrayList<Case>();
    @JsonProperty( value = "nextToken" )
    String nextToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty( "cases" )
    public List<Case> getCases() {
        return cases;
    }

    @JsonProperty( "cases" )
    public void setCases( List<Case> cases ) {
        this.cases = cases;
    }

    @JsonProperty( value = "nextToken" )
    public String getNextToken() {
        return nextToken;
    }

    @JsonProperty( value = "nextToken" )
    public void setNextToken( String nextToken ) {
        this.nextToken = nextToken;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty( String name, Object value ) {
        this.additionalProperties.put(name, value);
    }

}
