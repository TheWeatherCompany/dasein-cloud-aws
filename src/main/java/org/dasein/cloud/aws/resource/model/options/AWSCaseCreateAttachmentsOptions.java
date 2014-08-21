package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketAttachmentData;
import org.dasein.cloud.resource.model.options.TicketCreateAttachmentsOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseCreateAttachmentsOptions {

//    @JsonProperty("attachmentSetId")
//    private String attachmentSetId;
//    @JsonProperty("attachments")
//    private List<AWSCaseAttachmentData> attachments = new ArrayList<AWSCaseAttachmentData>();

    private TicketCreateAttachmentsOptions _options;

    private AWSCaseCreateAttachmentsOptions() {
    }

    private AWSCaseCreateAttachmentsOptions(TicketCreateAttachmentsOptions options) {
        this._options = options;
    }

    public static AWSCaseCreateAttachmentsOptions getInstance(TicketCreateAttachmentsOptions options) {
        return new AWSCaseCreateAttachmentsOptions(options);
    }

    @JsonProperty("attachmentSetId")
    public String getAttachmentSetId() {
        return _options.getAttachmentSetId();
    }

    @JsonProperty("attachments")
    public List<AWSCaseAttachmentData> getAttachments() {
        List<AWSCaseAttachmentData> attachments = new ArrayList<AWSCaseAttachmentData>();
        for(TicketAttachmentData data:_options.getAttachments()) {
            attachments.add(AWSCaseAttachmentData.getInstance(data));
        }
        return attachments;
    }
}
