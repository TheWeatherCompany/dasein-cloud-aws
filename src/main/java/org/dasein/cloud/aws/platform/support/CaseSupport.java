package org.dasein.cloud.aws.platform.support;

import org.apache.log4j.Logger;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.aws.platform.support.model.Case;
import org.dasein.cloud.aws.platform.support.model.CaseDetails;
import org.dasein.cloud.aws.platform.support.model.Communication;
import org.dasein.cloud.aws.platform.support.model.RecentCommunications;
import org.dasein.cloud.aws.platform.support.model.options.*;
import org.dasein.cloud.aws.platform.support.model.response.*;
import org.dasein.cloud.platform.support.AbstractTicketService;
import org.dasein.cloud.platform.support.model.*;
import org.dasein.cloud.platform.support.model.options.*;
import org.dasein.cloud.util.APITrace;
import org.dasein.util.Jiterator;
import org.dasein.util.JiteratorPopulator;
import org.dasein.util.PopulatorThread;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public class CaseSupport extends AbstractTicketService {

    static private final Logger logger = Logger.getLogger(CaseSupport.class);

    private AWSCloud provider = null;

    public CaseSupport( @Nonnull AWSCloud provider ) {
        super(provider);
        this.provider = provider;
    }

    @Override
    public Collection<Ticket> listTickets( @Nonnull final TicketListOptions options ) throws InternalException, CloudException {
        PopulatorThread<Ticket> populatorThread;

        final Boolean isIncludeReplies = options.getIncludeCommunications();
        options.setIncludeCommunications(false);

        provider.hold();
        populatorThread = new PopulatorThread<Ticket>(new JiteratorPopulator<Ticket>() {
            @Override
            public void populate( @Nonnull Jiterator<Ticket> ticketJiterator ) throws Exception {
                try {
                    populateTickets(ticketJiterator, CaseListOptions.getInstance(options), isIncludeReplies);
                } finally {
                    provider.release();
                }
            }
        });

        populatorThread.populate();
        return populatorThread.getResult();
    }

    @Override
    public void closeTicket( @Nonnull TicketCloseOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.closeTicket");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.RESOLVE_CASE);
            method.invoke(CaseCloseOptions.getInstance(options), Map.class);
        } finally {
            APITrace.end();
        }
    }

    @Override
    public String createTicket( @Nonnull TicketCreateOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.createTicket");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.CREATE_CASE);
            return method.invoke(CaseCreateOptions.getInstance(options), CaseCreateCaseResponse.class).getCaseId();
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Ticket getTicket( @Nonnull TicketGetOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.getTicket");
        try {
            Boolean isIncludeReplies = options.getIncludeCommunications();
            options.setIncludeCommunications(false);

            Case caze = getCase(CaseListOptions.getInstance(options));
            Ticket ticket = caze.buildTicket();
            if( isIncludeReplies != null && isIncludeReplies ) {
                ticket = fillTicketReplies(ticket);
            }
            return ticket;
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<TicketReply> listReplies( @Nonnull final TicketListRepliesOptions options ) throws InternalException, CloudException {
        PopulatorThread<TicketReply> populatorThread;

        provider.hold();
        populatorThread = new PopulatorThread<TicketReply>(new JiteratorPopulator<TicketReply>() {
            @Override
            public void populate( @Nonnull Jiterator<TicketReply> replyJiterator ) throws Exception {
                try {
                    populateReplies(replyJiterator, CaseListCommunicationOptions.getInstance(options));
                } finally {
                    provider.release();
                }
            }
        });

        populatorThread.populate();
        return populatorThread.getResult();

    }

    @Override
    public TicketAttachmentData getAttachment( @Nonnull TicketGetAttachmentOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.getAttachment");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_ATTACHMENT);
            return method.invoke(CaseAttachmentsListOptions.getInstance(options), CaseAttachmentResponse.class).getAttachment().buildAttachmentData();
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<TicketAttachment> listAttachments( @Nonnull TicketListAttachmentsOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listAttachments");
        try {
            Ticket ticket = getCase(CaseListOptions.getInstance(options)).buildTicket();
            ticket = fillTicketReplies(ticket);
            if( ticket.getRecentReplies() == null ) {
                return null;
            }
            List<TicketAttachment> result = new ArrayList<TicketAttachment>();
            for( TicketReply reply : ticket.getRecentReplies() ) {
                Collections.addAll(result, reply.getTicketAttachmentSet());
            }
            return result;
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<TicketService> listServices( @Nonnull TicketListServicesOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listServices");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_SERVICES);
            List<CaseServiceResponse> caseServiceResponses =  method.invoke(CaseListServicesOptions.getInstance(options), CaseListServicesResponse.class)
                    .getCaseServiceResponses();
            List<TicketService> listResult = new ArrayList<TicketService>();
            for( CaseServiceResponse caseServiceResponse : caseServiceResponses ) {
                listResult.add(caseServiceResponse.buildService());
            }
            return listResult;
        } finally {
            APITrace.end();
        }
    }

    @Override
    public void createReply( @Nonnull TicketCreateReplyOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.createReply");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.ADD_COMMUNICATION_TO_CASE);
            CaseCreateReplyResponse response = method.invoke(CaseCreateReplyOptions.getInstance(options), CaseCreateReplyResponse.class);
            if( !response.getResult().equalsIgnoreCase("true") ) {
                logger.error("Unable to create reply");
                throw new CloudException("Unable to create reply");
            }
        } finally {
            APITrace.end();
        }
    }

    @Override
    public String createAttachments( @Nonnull TicketCreateAttachmentsOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.createAttachments");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.ADD_ATTACHMENTS_TO_SET);
            String attachmentSetId = method.invoke(CaseCreateAttachmentsOptions.getInstance(options), CaseSetAttachmentResponse.class).getAttachmentSetId();
            TicketCreateReplyOptions createOptions = new TicketCreateReplyOptions();
            createOptions.setCaseId(options.getTicketId());
            createOptions.setAttachmentSetId(attachmentSetId);
            createOptions.setCommunicationBody("attachment file(s)");
            createReply(createOptions);

            return attachmentSetId;
        } finally {
            APITrace.end();
        }
    }

    /**
     * Returns single case, otherwise throws exception
     *
     * @param options the specified options
     * @return case
     * @throws InternalException
     * @throws CloudException
     */
    private Case getCase( CaseListOptions options ) throws InternalException, CloudException {
        CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_CASES);

        CaseDetails caseDetails = method.invoke(options, CaseDetails.class);
        List<Case> cases = caseDetails.getCases();
        if( cases == null ) {
            throw new CloudException("Response did not include any cases");
        }
        else if( cases.size() != 1 ) {
            throw new CloudException("Response include wrong amount of case [" + cases.size() + "]");
        }
        else {
            return cases.get(0);
        }
    }

    /**
     * Populates the replies having the specified options
     *
     * @param replyJiterator the replies jiterator
     * @param options        the specified options
     * @throws InternalException
     * @throws CloudException
     */
    private void populateReplies( Jiterator<TicketReply> replyJiterator, CaseListCommunicationOptions options ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listReplies");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_COMMUNICATIONS);
            RecentCommunications recentCommunications = method.invoke(options, RecentCommunications.class);
            for( Communication communication : recentCommunications.getCommunications() ) {
                replyJiterator.push(communication.buildReply());
            }
            String nextToken = recentCommunications.getNextToken();
            if( nextToken != null ) {
                options.setNextToken(nextToken);
                populateReplies(replyJiterator, options);
            }
        } finally {
            APITrace.end();
        }
    }

    /**
     * Populates the tickets having the specified options
     *
     * @param ticketJiterator the tickets jiterator
     * @param options         the specified options
     * @throws InternalException
     * @throws CloudException
     */
    private void populateTickets( Jiterator<Ticket> ticketJiterator, CaseListOptions options, Boolean isIncludeReplies ) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listTickets");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_CASES);

            CaseDetails caseDetails = method.invoke(options, CaseDetails.class);
            for( Case caze : caseDetails.getCases() ) {
                Ticket ticket;
                if( isIncludeReplies != null && isIncludeReplies ) {
                    ticket = fillTicketReplies(caze.buildTicket());
                }
                else {
                    ticket = caze.buildTicket();
                }
                ticketJiterator.push(ticket);
            }
            if( caseDetails.getNextToken() != null ) {
                options.setNextToken(caseDetails.getNextToken());
                populateTickets(ticketJiterator, options, isIncludeReplies);
            }
        } finally {
            APITrace.end();
        }
    }

    /**
     * Fills replies in ticket
     *
     * @param ticket ticket
     * @return incoming ticket with replies
     * @throws InternalException
     * @throws CloudException
     */
    private Ticket fillTicketReplies( Ticket ticket ) throws InternalException, CloudException {
        TicketListRepliesOptions options = new TicketListRepliesOptions();
        options.setTicketId(ticket.getTicketId());
        ticket.setRecentReplies(new ArrayList<TicketReply>(listReplies(options)));
        return ticket;
    }

}
