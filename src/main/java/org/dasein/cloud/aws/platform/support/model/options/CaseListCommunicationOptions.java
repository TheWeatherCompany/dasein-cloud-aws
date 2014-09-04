package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketListRepliesOptions;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
@JsonInclude( JsonInclude.Include.NON_NULL )
public class CaseListCommunicationOptions {

    @JsonIgnore
    private TicketListRepliesOptions _options;
    @JsonIgnore
    private String _nextToken;

    private CaseListCommunicationOptions() {
    }

    private CaseListCommunicationOptions( TicketListRepliesOptions options, String nextToken ) {
        this._nextToken = nextToken;
        this._options = options;
    }

    public static CaseListCommunicationOptions getInstance( TicketListRepliesOptions options ) {
        return new CaseListCommunicationOptions(options, null);
    }

    @JsonProperty( "afterTime" )
    public String getAfterTime() {
        return _options.getAfterTime();
    }

    @JsonProperty( "beforeTime" )
    public String getBeforeTime() {
        return _options.getBeforeTime();
    }

    @JsonProperty( "caseId" )
    public String getCaseId() {
        return _options.getTicketId();
    }

    @JsonProperty( "maxResults" )
    public Integer getMaxResults() {
        return 100;
    }

    @JsonProperty( "nextToken" )
    public String getNextToken() {
        return _nextToken;
    }

    @JsonIgnore
    public void setNextToken( String nextToken ) {
        _nextToken = nextToken;
    }

}
