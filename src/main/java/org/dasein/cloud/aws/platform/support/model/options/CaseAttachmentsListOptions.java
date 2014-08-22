package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketAttachmentsListOptions;
import org.dasein.cloud.platform.support.model.options.TicketGetAttachmetnOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseAttachmentsListOptions {

//    @JsonProperty(value = "attachmentId")
//    private String attachmentId;

    private TicketAttachmentsListOptions _options;

    private CaseAttachmentsListOptions() {
    }

    private CaseAttachmentsListOptions(TicketAttachmentsListOptions _options) {
        this._options = _options;
    }

    public static CaseAttachmentsListOptions getInstance(TicketAttachmentsListOptions options) {
        return new CaseAttachmentsListOptions(options);
    }

    public static CaseAttachmentsListOptions getInstance(TicketGetAttachmetnOptions options) {
        TicketAttachmentsListOptions ticketAttachmentsListOptions = new TicketAttachmentsListOptions();
        ticketAttachmentsListOptions.setAttachmentId(options.getAttachmentId());
        return new CaseAttachmentsListOptions(ticketAttachmentsListOptions);
    }

    @JsonProperty(value = "attachmentId")
    public String getAttachmentId() {
        return _options.getAttachmentId();
    }
}
