package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Eugene Yaroslavtsev
 * Date: 22.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"attachmentSetId", "expiryTime"})
public class CaseSetAttachmentResponse {

    @JsonProperty("attachmentSetId")
    private String attachmentSetId;
    @JsonProperty("expiryTime")
    private String expiryTime;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("attachmentSetId")
    public String getAttachmentSetId() {
        return attachmentSetId;
    }

    @JsonProperty("attachmentSetId")
    public void setAttachmentSetId( String attachmentSetId ) {
        this.attachmentSetId = attachmentSetId;
    }

    @JsonProperty("expiryTime")
    public String getExpiryTime() {
        return expiryTime;
    }

    @JsonProperty("expiryTime")
    public void setExpiryTime( String expiryTime ) {
        this.expiryTime = expiryTime;
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