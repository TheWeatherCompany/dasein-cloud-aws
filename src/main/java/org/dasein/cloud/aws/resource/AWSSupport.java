package org.dasein.cloud.aws.resource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import org.apache.log4j.Logger;
import org.dasein.cloud.CloudErrorType;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.aws.resource.model.Case;
import org.dasein.cloud.aws.resource.model.CaseDetails;
import org.dasein.cloud.aws.resource.model.Communication;
import org.dasein.cloud.aws.resource.model.RecentCommunications;
import org.dasein.cloud.aws.resource.model.options.*;
import org.dasein.cloud.aws.resource.model.response.AttachmentResponse;
import org.dasein.cloud.aws.resource.model.response.CreateCaseResponse;
import org.dasein.cloud.aws.resource.model.response.CreateReplyResponse;
import org.dasein.cloud.aws.resource.model.response.ListServicesResponse;
import org.dasein.cloud.resource.AbstractSupportService;
import org.dasein.cloud.resource.model.*;
import org.dasein.cloud.resource.model.options.*;
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
public class AWSSupport extends AbstractSupportService {

    static private final Logger logger = Logger.getLogger(AWSSupport.class);

    private AWSCloud provider = null;

    public AWSSupport(@Nonnull AWSCloud provider) {
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
                    populateTickets(ticketJiterator, AWSCaseListOptions.getInstance(options));
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
            SupportMethod method = new SupportMethod(provider, SupportTarget.RESOLVE_CASE);

            try {
                method.invoke(new ObjectMapper().writeValueAsString(AWSCaseCloseOptions.getInstance(options)));
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
            SupportMethod method = new SupportMethod(provider, SupportTarget.CREATE_CASE);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(AWSCaseCreateOptions.getInstance(options)));
                try {
                    return new ObjectMapper().readValue(result, CreateCaseResponse.class).getCaseId();
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
            Case caze = getCase(AWSCaseListOptions.getInstance(options));
            return caze.buildTicket();
        } finally {
            APITrace.end();
        }
    }

    @Override
    public ReplyWithCcEmails listReplies(@Nonnull final TicketListRepliesOptions options) throws InternalException, CloudException {
        PopulatorThread<Reply> populatorThread;

        provider.hold();
        populatorThread = new PopulatorThread<Reply>(new JiteratorPopulator<Reply>() {
            @Override
            public void populate(@Nonnull Jiterator<Reply> replyJiterator) throws Exception {
                try {
                    populateReplies(replyJiterator, AWSCaseListCommunicationOptions.getInstance(options));
                } finally {
                    provider.release();
                }
            }
        });

        TicketGetOptions ticketGetOptions = new TicketGetOptions();
        ticketGetOptions.setTicketId(options.getTicketId());
        Case caze = getCase(AWSCaseListOptions.getInstance(ticketGetOptions));

        populatorThread.populate();
        return new ReplyWithCcEmails(
                populatorThread.getResult(),
                caze.getCcEmailAddresses()
        );
    }

    @Override
    public AttachmentData getAttachment(@Nonnull TicketGetAttachmetnOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.getAttachment");
        try {
            SupportMethod method = new SupportMethod(provider, SupportTarget.DESCRIBE_ATTACHMENT);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(AWSCaseAttachmentsListOptions.getInstance(options)));
                return new ObjectMapper().readValue(result, AttachmentResponse.class).getAttachment();
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
    public Collection<Attachment> listAttachments(@Nonnull TicketListAttachmentsOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listAttachments");
        try {
            Case ca = getCase(AWSCaseListOptions.getInstance(options));
            if (ca.getRecentCommunications() == null) {
                return null;
            }
            List<Attachment> result = new ArrayList<Attachment>();
            for (Communication communication : ca.getRecentCommunications().getCommunications()) {
                for (org.dasein.cloud.aws.resource.model.Attachment attachment : communication.getAttachmentSet()) {
                    result.add(attachment.buildAttachment());
                }
            }
            return result;
        } finally {
            APITrace.end();
        }
    }

    @Override
    public Collection<Service> listServices(@Nonnull TicketListServicesOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listServices");
        try {
            SupportMethod method = new SupportMethod(provider, SupportTarget.DESCRIBE_SERVICES);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(AWSCaseListServicesOptions.getInstance(options)));
                List<org.dasein.cloud.aws.resource.model.response.Service> services = new ObjectMapper().readValue(result, ListServicesResponse.class).getServices();
                List<Service> listResult = new ArrayList<Service>();
                for (org.dasein.cloud.aws.resource.model.response.Service service : services) {
                    listResult.add(service.buildService());
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
            SupportMethod method = new SupportMethod(provider, SupportTarget.ADD_COMMUNICATION_TO_CASE);
            try {
                String result = method.invoke(new ObjectMapper().writeValueAsString(AWSCaseCreateReplyOptions.getInstance(options)));
                CreateReplyResponse response = new ObjectMapper().readValue(result, CreateReplyResponse.class);
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
            SupportMethod method = new SupportMethod(provider, SupportTarget.ADD_ATTACHMENTS_TO_SET);
            try {
                return method.invoke(new ObjectMapper().writeValueAsString(AWSCaseCreateAttachmentsOptions.getInstance(options)));
            } catch (IOException e) {
                logger.error(e);
                throw new CloudException("Unable to process parameters" + e.getMessage());
            }
        } finally {
            APITrace.end();
        }
    }

    private Case getCase(AWSCaseListOptions options) throws InternalException, CloudException {
        List<Case> cases = getCases(options);
        if (cases == null) {
            throw new CloudException("Response did not include any cases");
        } else if (cases.size() != 1) {
            throw new CloudException("Response include wrong amount of case [" + cases.size() + "]");
        } else {
            return cases.get(0);
        }
    }

    private List<Case> getCases(AWSCaseListOptions options) throws InternalException, CloudException {
        SupportMethod method = new SupportMethod(provider, SupportTarget.DESCRIBE_CASES);

        try {
            String result = method.invoke(new ObjectMapper().writeValueAsString(options));
            CaseDetails caseDetails = new ObjectMapper().readValue(result, CaseDetails.class);
            return caseDetails.getCases();
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
    private void populateReplies(Jiterator<Reply> replyJiterator, AWSCaseListCommunicationOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listReplies");
        try {
            SupportMethod method = new SupportMethod(provider, SupportTarget.DESCRIBE_COMMUNICATIONS);
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
    private void populateTickets(Jiterator<Ticket> ticketJiterator, AWSCaseListOptions options) throws InternalException, CloudException {
        APITrace.begin(provider, "Support.listTickets");
        try {
            SupportMethod method = new SupportMethod(provider, SupportTarget.DESCRIBE_CASES);

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
