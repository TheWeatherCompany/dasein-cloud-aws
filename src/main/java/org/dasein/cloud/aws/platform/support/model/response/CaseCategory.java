package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "code",
        "name"
})
public class CaseCategory {

    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public org.dasein.cloud.platform.support.model.TicketCategory build() {
        org.dasein.cloud.platform.support.model.TicketCategory category = new org.dasein.cloud.platform.support.model.TicketCategory();
        category.setName(name);
        category.setCode(code);
        return category;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode(String code) {
        this.code = code;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
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