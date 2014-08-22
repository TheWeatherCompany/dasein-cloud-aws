package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketCreateReplyOptions;

import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseCreateReplyOptions {

//    @JsonProperty("attachmentSetId")
//    private String attachmentSetId;
//    @JsonProperty("caseId")
//    private String caseId;
//    @JsonProperty("ccEmailAddresses")
//    private List<String> ccEmailAddresses = new ArrayList<String>();
//    @JsonProperty("communicationBody")
//    private String communicationBody;

    private TicketCreateReplyOptions _options;

    private CaseCreateReplyOptions() {
    }

    private CaseCreateReplyOptions(TicketCreateReplyOptions _options) {
        this._options = _options;
    }

    public static CaseCreateReplyOptions getInstance(TicketCreateReplyOptions options) {
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
    public List<String> getCcEmailAddresses() {
        return _options.getCcEmailAddresses();
    }

    @JsonProperty("communicationBody")
    public String getCommunicationBody() {
        return _options.getCommunicationBody();
    }

}
