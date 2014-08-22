package org.dasein.cloud.aws.platform.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.log4j.Logger;
import org.dasein.cloud.CloudErrorType;
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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public class CaseSupport extends AbstractTicketService {

    static private final Logger logger = Logger.getLogger(CaseSupport.class);

    private AWSCloud provider = null;

    public CaseSupport(@Nonnull AWSCloud provider) {
        super(provider);
        this.provider = provider;
    }

    @Override
    public Collection<Ticket> listTickets(@Nonnull final TicketListOptions options) throws InternalException, CloudException {
        PopulatorThread<Ticket> populatorThread;

        provider.hold();
        populatorThread = new PopulatorThread<Ticket>(new JiteratorPopulator<Ticket>() {
            @Override
            public void populate(@Nonnull Jiterator<Ticket> ticketJiterator) throws Exception {
                try {
                    populateTickets(ticketJiterator, CaseListOptions.getInstance(options));
                } finally {
                    provider.release();
                }
            }
        });

        populatorThread.populate();
        return populatorThread.getResult();
    }

    @Override
    public void closeTicket(@Nonnull TicketCloseOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.closeTicket");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.RESOLVE_CASE);

            try {
                method.invoke(new ObjectMapper().writeValueAsString(CaseCloseOptions.getInstance(options)));
            } catch (JsonProcessingException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            }
        } finally {
            APITrace.end();
        }
    }

    @Override
    public String createTicket(@Nonnull TicketCreateOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.createTicket");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.CREATE_CASE);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(CaseCreateOptions.getInstance(options)));
                try {
                    return new ObjectMapper().readValue(result, CaseCreateCaseResponse.class).getCaseId();
                } catch (UnrecognizedPropertyException e) {
                    logger.error("Error parsing response from AWS: " + e.getMessage());
                    throw new CloudException(CloudErrorType.COMMUNICATION, 500, null, e.getMessage());
                }
            } catch (JsonProcessingException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException(e);
            }
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Ticket getTicket(@Nonnull TicketGetOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.getTicket");
        try {
            Case caze = getCase(CaseListOptions.getInstance(options));
            return caze.buildTicket();
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<TicketReply> listReplies(@Nonnull final TicketListRepliesOptions options) throws InternalException, CloudException {
        PopulatorThread<TicketReply> populatorThread;

        provider.hold();
        populatorThread = new PopulatorThread<TicketReply>(new JiteratorPopulator<TicketReply>() {
            @Override
            public void populate(@Nonnull Jiterator<TicketReply> replyJiterator) throws Exception {
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
    public TicketAttachmentData getAttachment(@Nonnull TicketGetAttachmetnOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.getAttachment");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_ATTACHMENT);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(CaseAttachmentsListOptions.getInstance(options)));
                return new ObjectMapper().readValue(result, CaseAttachmentResponse.class).getAttachment().build();
            } catch (JsonProcessingException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException("Unable to process results" + e.getMessage());
            }
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<TicketAttachment> listAttachments(@Nonnull TicketListAttachmentsOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listAttachments");
        try {
            Case ca = getCase(CaseListOptions.getInstance(options));
            if (ca.getRecentCommunications() == null) {
                return null;
            }
            List<TicketAttachment> result = new ArrayList<TicketAttachment>();
            for (Communication communication : ca.getRecentCommunications().getCommunications()) {
                for (org.dasein.cloud.aws.platform.support.model.Attachment attachment : communication.getAttachmentSet()) {
                    result.add(attachment.buildAttachment());
                }
            }
            return result;
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<TicketService> listServices(@Nonnull TicketListServicesOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listServices");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_SERVICES);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(CaseListServicesOptions.getInstance(options)));
                List<CaseService> caseServices = new ObjectMapper().readValue(result, CaseListServicesResponse.class).getCaseServices();
                List<TicketService> listResult = new ArrayList<TicketService>();
                for (CaseService caseService : caseServices) {
                    listResult.add(caseService.buildService());
                }
                return listResult;
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            }
        } finally {
            APITrace.end();
        }
    }

    @Override
    public void createReply(@Nonnull TicketCreateReplyOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.createReply");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.ADD_COMMUNICATION_TO_CASE);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(CaseCreateReplyOptions.getInstance(options)));
                CaseCreateReplyResponse response = new ObjectMapper().readValue(result, CaseCreateReplyResponse.class);
                if (!response.getResult().equalsIgnoreCase("true")) {
                    logger.error("Unable to create reply");
                    throw new CloudException("Unable to create reply");
                }
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            }
        } finally {
            APITrace.end();
        }
    }

    @Override
    public String createAttachments(@Nonnull TicketCreateAttachmentsOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.createAttachments");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.ADD_ATTACHMENTS_TO_SET);
            try {
                String attachmentSetId = method.invoke(new ObjectMapper().writeValueAsString(CaseCreateAttachmentsOptions.getInstance(options)));
                TicketCreateReplyOptions createOptions = new TicketCreateReplyOptions();
                createOptions.setCaseId(options.getTicketId());
                createOptions.setAttachmentSetId(attachmentSetId);
                createReply(createOptions);
                return attachmentSetId;
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            }
        } finally {
            APITrace.end();
        }
    }

    private Case getCase(CaseListOptions options) throws InternalException, CloudException {
        CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_CASES);

        try {
            String result = method.invoke(new ObjectMapper().writeValueAsString(options));
            CaseDetails caseDetails = new ObjectMapper().readValue(result, CaseDetails.class);
            List<Case> cases = caseDetails.getCases();
            if (cases == null) {
                throw new CloudException("Response did not include any cases");
            } else if (cases.size() != 1) {
                throw new CloudException("Response include wrong amount of case [" + cases.size() + "]");
            } else {
                return cases.get(0);
            }
        } catch (JsonProcessingException e) {
            logger.error(e);
            throw new CloudException("Unable to process parameters" + e.getMessage());
        } catch (IOException e) {
            logger.error(e);
            throw new CloudException(e);
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
    private void populateReplies(Jiterator<TicketReply> replyJiterator, CaseListCommunicationOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listReplies");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_COMMUNICATIONS);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(options));
                RecentCommunications recentCommunications = new ObjectMapper().readValue(result, RecentCommunications.class);
                for (Communication communication : recentCommunications.getCommunications()) {
                    replyJiterator.push(communication.buildReply());
                }
                String nextToken = recentCommunications.getNextToken();
                if (nextToken != null) {
                    options.setNextToken(nextToken);
                    populateReplies(replyJiterator, options);
                }
            } catch (JsonProcessingException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException("Unable to process results" + e.getMessage());
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
    private void populateTickets(Jiterator<Ticket> ticketJiterator, CaseListOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listTickets");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_CASES);

            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(options));
                CaseDetails caseDetails = new ObjectMapper().readValue(result, CaseDetails.class);
                for (Case caze : caseDetails.getCases()) {
                    ticketJiterator.push(caze.buildTicket());
                }
                if (caseDetails.getNextToken() != null) {
                    options.setNextToken(caseDetails.getNextToken());
                    populateTickets(ticketJiterator, options);
                }
            } catch (JsonProcessingException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException(e);
            }
        } finally {
            APITrace.end();
        }
    }

}