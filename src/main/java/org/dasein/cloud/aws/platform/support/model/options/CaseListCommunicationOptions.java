package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.dasein.cloud.platform.support.model.options.TicketListRepliesOptions;

import java.util.HashMap;
import java.util.Map;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseListCommunicationOptions {

    @JsonIgnore
    private TicketListRepliesOptions _options;

    private CaseListCommunicationOptions() {
    }

    private CaseListCommunicationOptions(TicketListRepliesOptions _options) {
        this._options = _options;
    }

    public static CaseListCommunicationOptions getInstance(TicketListRepliesOptions options){
        options.setMaxResults(100);
        return new CaseListCommunicationOptions(options);
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
    public Integer getMaxResults() {
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
