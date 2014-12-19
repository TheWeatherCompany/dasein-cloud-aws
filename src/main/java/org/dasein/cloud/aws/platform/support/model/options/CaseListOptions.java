package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketGetOptions;
import org.dasein.cloud.platform.support.model.options.TicketListAttachmentsOptions;
import org.dasein.cloud.platform.support.model.options.TicketListOptions;
import org.dasein.cloud.platform.support.model.options.TicketListRepliesOptions;

/**
 * @author Eugene Yaroslavtsev
 * @since 14.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseListOptions {

    @JsonIgnore
    private TicketListOptions _options;
    @JsonIgnore
    private String _nextToken = null;
    @JsonIgnore
    private Integer _maxResults;

    private CaseListOptions() {
    }

    private CaseListOptions( TicketListOptions _options, Integer maxResults ) {
        this._maxResults = maxResults;
        this._options = _options;
    }

    public static CaseListOptions getInstance( TicketListOptions options ) {
        return new CaseListOptions(options, 100);
    }

    public static CaseListOptions getInstance( TicketGetOptions options ) {
        CaseListOptions caseListOptions = buildWithTicketId(options.getTicketId(), null);
        caseListOptions.withIncludeCommunications(options.getIncludeCommunications());
        return caseListOptions;
    }

    public static CaseListOptions getInstance( TicketListRepliesOptions options ) {
        return buildWithTicketId(options.getTicketId(), null);
    }

    public static CaseListOptions getInstance( TicketListAttachmentsOptions options ) {
        return buildWithTicketId(options.getTicketId(), null);
    }

    private static CaseListOptions buildWithTicketId( String id, Integer maxResults ) {
        TicketListOptions ticketListOptions = new TicketListOptions();
        ticketListOptions.setCaseIdList(new String[]{id});
        return new CaseListOptions(ticketListOptions, maxResults);
    }

    @JsonProperty(value = "afterTime")
    public String getAfterTime() {
        return _options.getAfterTime();
    }

    @JsonProperty(value = "beforeTime")
    public String getBeforeTime() {
        return _options.getBeforeTime();
    }

    @JsonProperty(value = "caseIdList")
    public String[] getCaseIdList() {
        return _options.getCaseIdList();
    }

    @JsonProperty(value = "displayId")
    public String getDisplayId() {
        return _options.getDisplayId();
    }

    @JsonProperty(value = "includeCommunications")
    public Boolean getIncludeCommunications() {
        return _options.getIncludeCommunications();
    }

    @JsonProperty(value = "includeResolvedCases")
    public Boolean getIncludeResolvedCases() {
        return _options.getIncludeResolvedCases();
    }

    @JsonProperty(value = "language")
    public String getLanguage() {
        return _options.getLanguage();
    }

    @JsonProperty(value = "maxResults")
    public Integer getMaxResults() {
        return _maxResults;
    }

    @JsonProperty(value = "nextToken")
    public String getNextToken() {
        return _nextToken;
    }

    @JsonIgnore
    public TicketListOptions getInOptions() {
        return _options;
    }

    @JsonIgnore
    public CaseListOptions withNextToken( String nextToken ) {
        this._nextToken = nextToken;
        return this;
    }

    @JsonIgnore
    public CaseListOptions withIncludeCommunications( Boolean includeCommunications ) {
        this._options.setIncludeCommunications(includeCommunications);
        return this;
    }
}
