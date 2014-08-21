package org.dasein.cloud.aws.resource.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.dasein.cloud.resource.model.AttachmentData;

/**
 * @author Eugene Yaroslavtsev
 * @since 20.08.2014
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({
        "attachment"
})
public class AttachmentResponse {

    @JsonProperty("attachment")
    private AttachmentData attachment;

    public AttachmentData getAttachment() {
        return attachment;
    }

    public void setAttachment(AttachmentData attachment) {
        this.attachment = attachment;
    }

}
