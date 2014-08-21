package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketAttachmentsListOptions;
import org.dasein.cloud.resource.model.options.TicketGetAttachmetnOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseAttachmentsListOptions {

//    @JsonProperty(value = "attachmentId")
//    private String attachmentId;

    private TicketAttachmentsListOptions _options;

    private AWSCaseAttachmentsListOptions() {
    }

    private AWSCaseAttachmentsListOptions(TicketAttachmentsListOptions _options) {
        this._options = _options;
    }

    public static AWSCaseAttachmentsListOptions getInstance(TicketAttachmentsListOptions options) {
        return new AWSCaseAttachmentsListOptions(options);
    }

    public static AWSCaseAttachmentsListOptions getInstance(TicketGetAttachmetnOptions options) {
        TicketAttachmentsListOptions ticketAttachmentsListOptions = new TicketAttachmentsListOptions();
        ticketAttachmentsListOptions.setAttachmentId(options.getAttachmentId());
        return new AWSCaseAttachmentsListOptions();
    }

    @JsonProperty(value = "attachmentId")
    public String getAttachmentId() {
        return _options.getAttachmentId();
    }
}
