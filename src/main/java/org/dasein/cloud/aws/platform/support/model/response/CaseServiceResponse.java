package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;
import org.dasein.cloud.platform.support.model.TicketCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"__type", "categories", "code", "name"})
public class CaseServiceResponse {

    @JsonProperty("__type")
    private String type;
    @JsonProperty("categories")
    private List<CaseCategoryResponse> categories = new ArrayList<CaseCategoryResponse>();
    @JsonProperty("code")
    private String code;
    @JsonProperty("name")
    private String name;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public org.dasein.cloud.platform.support.model.TicketService buildService() {
        org.dasein.cloud.platform.support.model.TicketService service = new org.dasein.cloud.platform.support.model.TicketService();
        service.setCode(code);
        service.setName(name);
        List<org.dasein.cloud.platform.support.model.TicketCategory> list = new ArrayList<org.dasein.cloud.platform.support.model.TicketCategory>();
        for( CaseCategoryResponse caseCategoryResponse : categories ) {
            list.add(caseCategoryResponse.build());
        }
        service.setCategories(list.toArray(new TicketCategory[list.size()]));
        return service;
    }

    @JsonProperty("__type")
    public String getType() {
        return type;
    }

    @JsonProperty("__type")
    public void setType( String type ) {
        this.type = type;
    }

    @JsonProperty("categories")
    public List<CaseCategoryResponse> getCategories() {
        return categories;
    }

    @JsonProperty("categories")
    public void setCategories( List<CaseCategoryResponse> categories ) {
        this.categories = categories;
    }

    @JsonProperty("code")
    public String getCode() {
        return code;
    }

    @JsonProperty("code")
    public void setCode( String code ) {
        this.code = code;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName( String name ) {
        this.name = name;
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