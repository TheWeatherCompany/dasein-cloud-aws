package org.dasein.cloud.aws.resource.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.dasein.cloud.resource.model.options.TicketListRepliesOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "AfterTime",
        "BeforeTime",
        "CaseId",
        "MaxResults",
        "NextToken"
})
public class AWSCaseListCommunicationOptions {

//    @JsonProperty("AfterTime")
//    private String afterTime;
//    @JsonProperty("BeforeTime")
//    private String beforeTime;
//    @JsonProperty("CaseId")
//    private String caseId;
//    @JsonProperty("MaxResults")
//    private String maxResults;
//    @JsonProperty("NextToken")
//    private String nextToken;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    private TicketListRepliesOptions _options;

    private AWSCaseListCommunicationOptions() {
    }

    private AWSCaseListCommunicationOptions(TicketListRepliesOptions _options) {
        this._options = _options;
    }

    public static AWSCaseListCommunicationOptions getInstance(TicketListRepliesOptions options){
        return new AWSCaseListCommunicationOptions(options);
    }

    @JsonProperty("afterTime")
    public String getAfterTime() {
        return _options.getAfterTime();
    }

    @JsonProperty("beforeTime")
    public String getBeforeTime() {
        return _options.getBeforeTime();
    }

    @JsonProperty("caseId")
    public String getCaseId() {
        return _options.getTicketId();
    }

    @JsonProperty("maxResults")
    public String getMaxResults() {
        return _options.getMaxResults();
    }

    @JsonProperty("nextToken")
    public String getNextToken() {
        return _options.getNextToken();
    }

    public void setNextToken(String nextToken) {
        _options.setNextToken(nextToken);
    }


}
