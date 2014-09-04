package org.dasein.cloud.aws.platform.support.model.options;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.dasein.cloud.platform.support.model.options.TicketGetOptions;
import org.dasein.cloud.platform.support.model.options.TicketListAttachmentsOptions;
import org.dasein.cloud.platform.support.model.options.TicketListOptions;
import org.dasein.cloud.platform.support.model.options.TicketListRepliesOptions;

import java.util.Arrays;
import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 14.08.2014
 */
@JsonInclude(Include.NON_NULL)
public class CaseListOptions {

    @JsonIgnore
    private TicketListOptions _options;

    private CaseListOptions() {
    }

    private CaseListOptions( TicketListOptions _options ) {
        this._options = _options;
    }

    public static CaseListOptions getInstance( TicketListOptions options ) {
        options.setMaxResults(100);
        return new CaseListOptions(options);
    }

    public static CaseListOptions getInstance( TicketGetOptions options ) {
        CaseListOptions caseListOptions = setId(options.getTicketId());
        caseListOptions.setIncludeCommunications(options.getIncludeCommunications());
        return caseListOptions;
    }

    public static CaseListOptions getInstance( TicketListRepliesOptions options ) {
        return setId(options.getTicketId());
    }

    public static CaseListOptions getInstance( TicketListAttachmentsOptions options ) {
        return setId(options.getTicketId());
    }

    private static CaseListOptions setId( String id ) {
        TicketListOptions ticketListOptions = new TicketListOptions();
        ticketListOptions.setCaseIdList(Arrays.asList(id));
        return new CaseListOptions(ticketListOptions);
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
    public List<String> getCaseIdList() {
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
        return _options.getMaxResults();
    }

    @JsonProperty(value = "nextToken")
    public String getNextToken() {
        return _options.getNextToken();
    }

    @JsonIgnore
    public TicketListOptions getInOptions() {
        return _options;
    }

    @JsonIgnore
    public void setNextToken( String nextToken ) {
        _options.setNextToken(nextToken);
    }

    @JsonIgnore
    public void setIncludeCommunications( Boolean includeCommunications ) {
        _options.setIncludeCommunications(includeCommunications);
    }
}
