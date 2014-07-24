package org.dasein.cloud.aws.quotas;

import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.quotas.AbstractQuotaServices;

import javax.annotation.Nullable;

/**
 * User: mgulimonov
 * Date: 25.07.2014
 */
public class EC2QuotaService extends AbstractQuotaServices {

    private AWSCloud cloud;

    public EC2QuotaService(AWSCloud cloud) { this.cloud = cloud; }

    @Override
    public @Nullable EC2QuotaSupport getQuotaSupport() {
        return new EC2QuotaSupport(cloud);
    }

}
