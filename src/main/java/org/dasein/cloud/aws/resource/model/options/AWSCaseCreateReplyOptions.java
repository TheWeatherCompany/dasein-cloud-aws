package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketCreateReplyOptions;

import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 18.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseCreateReplyOptions {

//    @JsonProperty("attachmentSetId")
//    private String attachmentSetId;
//    @JsonProperty("caseId")
//    private String caseId;
//    @JsonProperty("ccEmailAddresses")
//    private List<String> ccEmailAddresses = new ArrayList<String>();
//    @JsonProperty("communicationBody")
//    private String communicationBody;

    private TicketCreateReplyOptions _options;

    private AWSCaseCreateReplyOptions() {
    }

    private AWSCaseCreateReplyOptions(TicketCreateReplyOptions _options) {
        this._options = _options;
    }

    public static AWSCaseCreateReplyOptions getInstance(TicketCreateReplyOptions options) {
        return new AWSCaseCreateReplyOptions(options);
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
