package org.dasein.cloud.aws.platform.support.model;

import java.util.List;

/**
 * @author Eugene Yaroslavtsev
 * @since 15.08.2014
 */
public class CommunicationWithCcEmails {
    private List<Communication> communication;
    private List<String> ccEmailAddresses;

    public CommunicationWithCcEmails(List<Communication> communication, List<String> ccEmailAddresses) {
        this.communication = communication;
        this.ccEmailAddresses = ccEmailAddresses;
    }

    public List<Communication> getCommunication() {
        return communication;
    }

    public void setCommunication(List<Communication> communication) {
        this.communication = communication;
    }

    public List<String> getCcEmailAddresses() {
        return ccEmailAddresses;
    }

    public void setCcEmailAddresses(List<String> ccEmailAddresses) {
        this.ccEmailAddresses = ccEmailAddresses;
    }
}
