package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.resource.model.options.TicketCreateOptions;

import java.util.Collection;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class AWSCaseCreateOptions {

//    @JsonProperty(value = "attachmentSetId")
//    String attachmentSetId;
//
//    @JsonProperty(value = "categoryCode")
//    String categoryCode;
//
//    @JsonProperty(value = "ccEmailAddresses")
//    Collection<String> ccEmailAddresses;
//
//    @JsonProperty(value = "communicationBody")
//    String communicationBody;
//
//    @JsonProperty(value = "issueType")
//    String issueType;
//
//    @JsonProperty(value = "language")
//    String language;
//
//    @JsonProperty(value = "serviceCode")
//    String serviceCode;
//
//    @JsonProperty(value = "severityCode")
//    String severityCode;
//
//    @JsonProperty(value = "subject")
//    String subject;

    private TicketCreateOptions _options;

    private AWSCaseCreateOptions() {
    }

    private AWSCaseCreateOptions(TicketCreateOptions options) {
        this._options = options;
    }

    public static AWSCaseCreateOptions getInstance(TicketCreateOptions options) {
       return new AWSCaseCreateOptions(options);
    }
    @JsonProperty(value = "attachmentSetId")
    public String getAttachmentSetId() {
        return _options.getAttachmentSetId();
    }

    @JsonProperty(value = "categoryCode")
    public String getCategoryCode() {
        return _options.getCategoryCode();
    }

    @JsonProperty(value = "ccEmailAddresses")
    public Collection<String> getCcEmailAddresses() {
        return _options.getCcEmailAddresses();
    }

    @JsonProperty(value = "communicationBody")
    public String getCommunicationBody() {
        return _options.getCommunicationBody();
    }

    @JsonProperty(value = "issueType")
    public String getIssueType() {
        return _options.getIssueType();
    }

    @JsonProperty(value = "language")
    public String getLanguage() {
        return _options.getLanguage();
    }

    @JsonProperty(value = "serviceCode")
    public String getServiceCode() {
        return _options.getServiceCode();
    }

    @JsonProperty(value = "severityCode")
    public String getSeverityCode() {
        return _options.getSeverityCode();
    }

    @JsonProperty(value = "subject")
    public String getSubject() {
        return _options.getSubject();
    }
}
