package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketCreateReplyOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseCreateReplyOptions {

    @JsonIgnore
    private TicketCreateReplyOptions _options;

    private CaseCreateReplyOptions() {
    }

    private CaseCreateReplyOptions( TicketCreateReplyOptions _options ) {
        this._options = _options;
    }

    public static CaseCreateReplyOptions getInstance( TicketCreateReplyOptions options ) {
        return new CaseCreateReplyOptions(options);
    }

    @JsonProperty("attachmentSetId")
    public String getAttachmentSetId() {
        return _options.getAttachmentSetId();
    }

    @JsonProperty("caseId")
    public String getCaseId() {
        return _options.getCaseId();
    }

    @JsonProperty("ccEmailAddresses")
    public String[] getCcEmailAddresses() {
        return _options.getCcEmailAddresses();
    }

    @JsonProperty("communicationBody")
    public String getCommunicationBody() {
        return _options.getCommunicationBody();
    }

}
