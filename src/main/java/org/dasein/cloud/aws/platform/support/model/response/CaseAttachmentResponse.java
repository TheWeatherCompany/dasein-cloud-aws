package org.dasein.cloud.aws.platform.support.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Eugene Yaroslavtsev
 * @since 20.08.2014
 */
@JsonInclude(Include.NON_NULL)
@JsonPropertyOrder({
        "attachment"
})
public class CaseAttachmentResponse {

    @JsonProperty("attachment")
    private CaseAttachmentData attachment;

    public CaseAttachmentData getAttachment() {
        return attachment;
    }

    public void setAttachment(CaseAttachmentData attachment) {
        this.attachment = attachment;
    }

}
