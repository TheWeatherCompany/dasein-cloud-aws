package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketAttachmentDataOptions;
import org.dasein.cloud.platform.support.model.options.TicketCreateAttachmentsOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 19.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseCreateAttachmentsOptions {

    @JsonIgnore
    private TicketCreateAttachmentsOptions _options;

    private CaseCreateAttachmentsOptions() {
    }

    private CaseCreateAttachmentsOptions( TicketCreateAttachmentsOptions options ) {
        this._options = options;
    }

    public static CaseCreateAttachmentsOptions getInstance( TicketCreateAttachmentsOptions options ) {
        return new CaseCreateAttachmentsOptions(options);
    }

    @JsonProperty("attachments")
    public List<CaseAttachmentDataOptions> getAttachments() {
        List<CaseAttachmentDataOptions> attachments = new ArrayList<CaseAttachmentDataOptions>();
        for( TicketAttachmentDataOptions data : _options.getAttachments() ) {
            attachments.add(CaseAttachmentDataOptions.getInstance(data));
        }
        return attachments;
    }
}
