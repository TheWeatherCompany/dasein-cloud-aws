package org.dasein.cloud.aws.platform.support;

import org.dasein.cloud.platform.support.TicketStatus;

/**
 * @author Eugene Yaroslavtsev
 * @since 26.08.2014
 */
public enum CaseStatus {
    UNASSIGNED( "unassigned" ),
    PENDING_CUSTOMER( "pending-customer-action" ),
    PENDING_PROVIDER( "pending-provider-action" ),
    CUSTOMER_ACTION_COMPLETED( "customer-action-completed" ),
    RESOLVED( "resolved" ),
    REOPENED( "reopened" ),
    UNKNOWN( "unknown-action" );

    private String name;

    CaseStatus(String name) {
        this.name = name;
    }

    public static CaseStatus valueOf(Object value) {
        for (CaseStatus status : values()) {
            if (status.name.equalsIgnoreCase(value.toString())) {
                return status;
            }
        }
        return UNKNOWN;//default
    }

    public static TicketStatus buildTicketStatus(CaseStatus caseStatus) {
        switch (caseStatus) {
            case UNASSIGNED:
                return TicketStatus.ASSIGNED_TO_PROVIDER;
            case PENDING_CUSTOMER:
                return TicketStatus.ASSIGNED_TO_CUSTOMER;
            case PENDING_PROVIDER:
                return TicketStatus.ASSIGNED_TO_PROVIDER;
            case CUSTOMER_ACTION_COMPLETED:
                return TicketStatus.ASSIGNED_TO_PROVIDER;
            case RESOLVED:
                return TicketStatus.CLOSED;
            case REOPENED:
                return TicketStatus.ASSIGNED_TO_PROVIDER;
            default:
                return TicketStatus.UNKNOWN;
        }
    }

}
