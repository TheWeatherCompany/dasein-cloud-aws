package org.dasein.cloud.aws.quotas;

import org.apache.log4j.Logger;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.aws.platform.support.CaseSupportMethod;
import org.dasein.cloud.aws.platform.support.CaseSupportTarget;
import org.dasein.cloud.aws.quotas.model.*;
import org.dasein.cloud.quotas.AbstractQuotaSupport;
import org.dasein.cloud.quotas.CloudResourceScope;
import org.dasein.cloud.quotas.CloudResourceType;
import org.dasein.cloud.quotas.QuotaDescriptor;
import org.dasein.cloud.util.APITrace;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: mgulimonov
 * Date: 23.07.2014
 */
public class EC2QuotaSupport extends AbstractQuotaSupport {

    static private final Logger logger = AWSCloud.getLogger(EC2QuotaSupport.class);
    private AWSCloud provider;

    public EC2QuotaSupport(AWSCloud provider) {
        this.provider = provider;
    }

    @Override
    public Collection<QuotaDescriptor> getQuotas(String regionId) throws CloudException, InternalException {
        APITrace.begin(getProvider(), "getQuotas");
        try {
            CaseSupportMethod method = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_TRUSTED_ADVISOR_CHECKS);
            TrustedAdvisorCheckDescription description = method.invoke(
                    new TrustedAdvisorChecksRequest("en"), TrustedAdvisorChecks.class
            ).getServiceLimitDescription();

            CaseSupportMethod checkResultMethod = new CaseSupportMethod(provider, CaseSupportTarget.DESCRIBE_TRUSTED_ADVISOR_CHECK_RESULT);
            TrustedAdvisorCheckResponse response = checkResultMethod.invoke(
                    new DescribeTrustedAdvisorCheckRequest(description.getId(), "en"),
                    TrustedAdvisorCheckResponse.class
            );

            return toQuotaDescriptors(response.getTrustedAdvisorCheckResult().getFlaggedResources(), regionId);
        } finally {
            APITrace.end();
        }
    }

    private Collection<QuotaDescriptor> toQuotaDescriptors(List<FlaggedResource> resources, String regionId) {
        Collection<QuotaDescriptor> result = new ArrayList<QuotaDescriptor>(resources.size());
        for (FlaggedResource resource : resources) {
            if (resource.getRegion().equalsIgnoreCase(regionId)) {
                QuotaDescriptor descriptor = toQuotaDescriptor(resource);
                if (descriptor.getResourceType() != null) {
                    result.add(descriptor);
                }
            }
        }
        return result;
    }

    private QuotaDescriptor toQuotaDescriptor(FlaggedResource resource) {
        logger.debug("Adding new resource desciptor: " + resource);

        return new QuotaDescriptor(
                        resourceType(resource),
                        CloudResourceScope.REGION,
                        resource.quota(),
                        resource.usage()
                );
    }

    private CloudResourceType resourceType(FlaggedResource resource) {
        if ("AutoScaling".equals(resource.serviceType()) && resource.limitName().contains("Auto Scaling")) {
            return CloudResourceType.AUTO_SCALING_GROUP;
        }
        if ("AutoScaling".equals(resource.serviceType()) && resource.limitName().contains("Launch configurations")) {
            return CloudResourceType.LAUNCH_CONFIG;
        }
        if ("EBS".equals(resource.serviceType()) && resource.limitName().contains("snapshots")) {
            return CloudResourceType.SNAPSHOT;
        }
        if ("EBS".equals(resource.serviceType()) && resource.limitName().contains("volumes")) {
            return CloudResourceType.VOLUME;
        }
        if ("VPC".equals(resource.serviceType()) && resource.limitName().contains("gateways")) {
            return CloudResourceType.INTERNET_GATEWAY;
        }
        if ("VPC".equals(resource.serviceType()) && resource.limitName().contains("addresses")) {
            return CloudResourceType.IP_ADDRESS;
        }
        if ("ELB".equals(resource.serviceType()) && resource.limitName().contains("balancers")) {
            return CloudResourceType.LOAD_BALANCER;
        }
        if ("EC2".equals(resource.serviceType()) && resource.limitName().contains("Instances")) {
            return CloudResourceType.INSTANCE;
        }

        return null;
    }

    public AWSCloud getProvider() {
        return provider;
    }
}
