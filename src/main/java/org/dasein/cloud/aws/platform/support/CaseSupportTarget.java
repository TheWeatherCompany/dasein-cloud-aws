package org.dasein.cloud.aws.platform.support;

/**
 * User: Eugene Yaroslavtsev
 * Date: 21.08.2014
 */
public enum CaseSupportTarget {

    DESCRIBE_CASES("AWSSupport_20130415.DescribeCases"),
    DESCRIBE_ATTACHMENT("AWSSupport_20130415.DescribeAttachment"),
    RESOLVE_CASE("AWSSupport_20130415.ResolveCase"),
    CREATE_CASE("AWSSupport_20130415.CreateCase"),
    DESCRIBE_SERVICES("AWSSupport_20130415.DescribeServices"),
    ADD_COMMUNICATION_TO_CASE("AWSSupport_20130415.AddCommunicationToCase"),
    ADD_ATTACHMENTS_TO_SET("AWSSupport_20130415.AddAttachmentsToSet"),
    DESCRIBE_COMMUNICATIONS("AWSSupport_20130415.DescribeCommunications"),
    DESCRIBE_TRUSTED_ADVISOR_CHECKS("AWSSupport_20130415.DescribeTrustedAdvisorChecks"),
    DESCRIBE_TRUSTED_ADVISOR_CHECK_RESULT("AWSSupport_20130415.DescribeTrustedAdvisorCheckResult")
    ;

    private String target;

    CaseSupportTarget( String target ) {
        this.target = target;
    }

    public String getTarget() {
        return target;
    }
}
