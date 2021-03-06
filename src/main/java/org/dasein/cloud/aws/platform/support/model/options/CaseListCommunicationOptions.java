package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketListRepliesOptions;

import javax.annotation.Nonnull;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaseListCommunicationOptions {

    @JsonIgnore
    private TicketListRepliesOptions _options;
    @JsonIgnore
    private String _nextToken = null;
    @JsonIgnore
    private Integer _maxResults;

    private CaseListCommunicationOptions() {
    }

    private CaseListCommunicationOptions( TicketListRepliesOptions options, Integer maxResults ) {
        this._maxResults = maxResults;
        this._options = options;
    }

    public static CaseListCommunicationOptions getInstance( TicketListRepliesOptions options ) {
        return new CaseListCommunicationOptions(options, 100);
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
        return _maxResults;
    }

    @JsonProperty("nextToken")
    public String getNextToken() {
        return _nextToken;
    }

    @JsonIgnore
    public @Nonnull CaseListCommunicationOptions withNextToken( String nextToken ) {
        this._nextToken = nextToken;
        return this;
    }

}
