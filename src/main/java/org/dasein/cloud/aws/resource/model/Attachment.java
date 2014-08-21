package org.dasein.cloud.aws.resource.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eugene Yaroslavtsev
 * @since 14.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attachmentId",
        "fileName"
})
public class Attachment {

    @JsonProperty("attachmentId")
    private String attachmentId;
    @JsonProperty("fileName")
    private String fileName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public org.dasein.cloud.resource.model.Attachment buildAttachment() {
        org.dasein.cloud.resource.model.Attachment attachment = new org.dasein.cloud.resource.model.Attachment();
        attachment.setAttachmentId(attachmentId);
        attachment.setFileName(fileName);
        return attachment;
    }

    @JsonProperty("attachmentId")
    public String getAttachmentId() {
        return attachmentId;
    }

    @JsonProperty("attachmentId")
    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    @JsonProperty("fileName")
    public String getFileName() {
        return fileName;
    }

    @JsonProperty("fileName")
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
