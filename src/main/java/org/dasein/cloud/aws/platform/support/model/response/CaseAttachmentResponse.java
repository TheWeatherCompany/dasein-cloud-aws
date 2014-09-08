package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene Yaroslavtsev
 * @since 20.08.2014
 */
@JsonInclude( Include.NON_NULL )
@JsonPropertyOrder( {"attachment"} )
public class CaseAttachmentResponse {

    @JsonProperty( "attachment" )
    private CaseAttachmentDataResponse attachment;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty( "attachment" )
    public CaseAttachmentDataResponse getAttachment() {
        return attachment;
    }

    @JsonProperty( "attachment" )
    public void setAttachment( CaseAttachmentDataResponse attachment ) {
        this.attachment = attachment;
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
