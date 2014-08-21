
package org.dasein.cloud.aws.resource.model;

import com.fasterxml.jackson.annotation.*;
import org.dasein.cloud.resource.model.Reply;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "attachmentSet",
        "body",
        "caseId",
        "submittedBy",
        "timeCreated"
})
public class Communication {

    @JsonProperty("attachmentSet")
    private List<Attachment> attachmentSet;
    @JsonProperty("body")
    private String body;
    @JsonProperty("caseId")
    private String caseId;
    @JsonProperty("submittedBy")
    private String submittedBy;
    @JsonProperty("timeCreated")
    private String timeCreated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public Reply buildReply() {
        Reply reply = new Reply();
        List<org.dasein.cloud.resource.model.Attachment> attachments = new ArrayList<org.dasein.cloud.resource.model.Attachment>();
        for(Attachment attachment: attachmentSet) {
            attachments.add(attachment.buildAttachment());
        }
        reply.setAttachmentSet(attachments);
        reply.setBody(body);
        reply.setTicketId(caseId);
        reply.setSubmittedBy(submittedBy);
        reply.setTimeCreated(timeCreated);
        return reply;
    }

    @JsonProperty("attachmentSet")
    public List<Attachment> getAttachmentSet() {
        return attachmentSet;
    }

    @JsonProperty("attachmentSet")
    public void setAttachmentSet(List<Attachment> attachmentSet) {
        this.attachmentSet = attachmentSet;
    }

    @JsonProperty("body")
    public String getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(String body) {
        this.body = body;
    }

    @JsonProperty("caseId")
    public String getCaseId() {
        return caseId;
    }

    @JsonProperty("caseId")
    public void setCaseId(String caseId) {
        this.caseId = caseId;
    }

    @JsonProperty("submittedBy")
    public String getSubmittedBy() {
        return submittedBy;
    }

    @JsonProperty("submittedBy")
    public void setSubmittedBy(String submittedBy) {
        this.submittedBy = submittedBy;
    }

    @JsonProperty("timeCreated")
    public String getTimeCreated() {
        return timeCreated;
    }

    @JsonProperty("timeCreated")
    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
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
