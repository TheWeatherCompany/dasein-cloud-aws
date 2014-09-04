package org.dasein.cloud.aws.platform.support.model;

import com.fasterxml.jackson.annotation.*;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.aws.platform.support.CaseStatus;
import org.dasein.cloud.platform.support.TicketSeverity;
import org.dasein.cloud.platform.support.model.TicketReply;
import org.dasein.cloud.platform.support.model.Ticket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude( JsonInclude.Include.NON_NULL )
@JsonPropertyOrder( {"__type", "caseId", "categoryCode", "ccEmailAddresses", "displayId", "language", "recentCommunications", "serviceCode", "severityCode", "status", "subject", "submittedBy", "timeCreated"} )
public class Case {

    @JsonProperty( "__type" )
    private String type;
    @JsonProperty( "caseId" )
    private String caseId;
    @JsonProperty( "categoryCode" )
    private String categoryCode;
    @JsonProperty( "ccEmailAddresses" )
    private List<String> ccEmailAddresses = new ArrayList<String>();
    @JsonProperty( "displayId" )
    private String displayId;
    @JsonProperty( "language" )
    private String language;
    @JsonProperty( "recentCommunications" )
    private RecentCommunications recentCommunications;
    @JsonProperty( "serviceCode" )
    private String serviceCode;
    @JsonProperty( "severityCode" )
    private String severityCode;
    @JsonProperty( "status" )
    private String status;
    @JsonProperty( "subject" )
    private String subject;
    @JsonProperty( "submittedBy" )
    private String submittedBy;
    @JsonProperty( "timeCreated" )
    private String timeCreated;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonIgnore
    public Ticket buildTicket() throws CloudException {
        Ticket ticket = new Ticket();
        ticket.setTicketId(caseId);
        ticket.setCategoryCode(categoryCode);
        ticket.setCcEmailAddresses(ccEmailAddresses);
        ticket.setDisplayId(displayId);
        ticket.setLanguage(language);
        List<TicketReply> replies = new ArrayList<TicketReply>();
        if( recentCommunications != null ) {
            for( Communication communication : recentCommunications.getCommunications() ) {
                replies.add(communication.buildReply());
            }
        }
        ticket.setRecentReplies(replies);
        ticket.setServiceCode(serviceCode);
        ticket.setSeverityCode(TicketSeverity.valueOf(( Object ) severityCode));
        ticket.setStatus(CaseStatus.buildTicketStatus(CaseStatus.valueOf(( Object ) status)));
        ticket.setSubject(subject);
        ticket.setSubmittedBy(submittedBy);
        ticket.setTimeCreated(timeCreated);
        return ticket;
    }

    @JsonProperty( "__type" )
    public String getType() {
        return type;
    }

    @JsonProperty( "__type" )
    public void setType( String type ) {
        this.type = type;
    }

    @JsonProperty( "caseId" )
    public String getCaseId() {
        return caseId;
    }

    @JsonProperty( "caseId" )
    public void setCaseId( String caseId ) {
        this.caseId = caseId;
    }

    @JsonProperty( "categoryCode" )
    public String getCategoryCode() {
        return categoryCode;
    }

    @JsonProperty( "categoryCode" )
    public void setCategoryCode( String categoryCode ) {
        this.categoryCode = categoryCode;
    }

    @JsonProperty( "ccEmailAddresses" )
    public List<String> getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    @JsonProperty( "ccEmailAddresses" )
    public void setCcEmailAddresses( List<String> ccEmailAddresses ) {
        this.ccEmailAddresses = ccEmailAddresses;
    }

    @JsonProperty( "displayId" )
    public String getDisplayId() {
        return displayId;
    }

    @JsonProperty( "displayId" )
    public void setDisplayId( String displayId ) {
        this.displayId = displayId;
    }

    @JsonProperty( "language" )
    public String getLanguage() {
        return language;
    }

    @JsonProperty( "language" )
    public void setLanguage( String language ) {
        this.language = language;
    }

    @JsonProperty( "recentCommunications" )
    public RecentCommunications getRecentCommunications() {
        return recentCommunications;
    }

    @JsonProperty( "recentCommunications" )
    public void setRecentCommunications( RecentCommunications recentCommunications ) {
        this.recentCommunications = recentCommunications;
    }

    @JsonProperty( "serviceCode" )
    public String getServiceCode() {
        return serviceCode;
    }

    @JsonProperty( "serviceCode" )
    public void setServiceCode( String serviceCode ) {
        this.serviceCode = serviceCode;
    }

    @JsonProperty( "severityCode" )
    public String getSeverityCode() {
        return severityCode;
    }

    @JsonProperty( "severityCode" )
    public void setSeverityCode( String severityCode ) {
        this.severityCode = severityCode;
    }

    @JsonProperty( "status" )
    public String getStatus() {
        return status;
    }

    @JsonProperty( "status" )
    public void setStatus( String status ) {
        this.status = status;
    }

    @JsonProperty( "subject" )
    public String getSubject() {
        return subject;
    }

    @JsonProperty( "subject" )
    public void setSubject( String subject ) {
        this.subject = subject;
    }

    @JsonProperty( "submittedBy" )
    public String getSubmittedBy() {
        return submittedBy;
    }

    @JsonProperty( "submittedBy" )
    public void setSubmittedBy( String submittedBy ) {
        this.submittedBy = submittedBy;
    }

    @JsonProperty( "timeCreated" )
    public String getTimeCreated() {
        return timeCreated;
    }

    @JsonProperty( "timeCreated" )
    public void setTimeCreated( String timeCreated ) {
        this.timeCreated = timeCreated;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty( String name, Object value ) {
        this.additionalProperties.put(name, value);
    }


}
