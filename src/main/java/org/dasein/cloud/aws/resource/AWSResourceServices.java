package org.dasein.cloud.aws.resource;

import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.resource.ResourceServices;
import org.dasein.cloud.resource.SupportService;

import javax.annotation.Nullable;

/**
 * User: Eugene Yaroslavtsev
 * Date: 20.08.2014
 */
public class AWSResourceServices implements ResourceServices {

    private AWSCloud cloud;

    public AWSResourceServices(AWSCloud cloud) {
        this.cloud = cloud;
    }

    @Override
    public @Nullable SupportService getSupportService() {
        return new AWSSupport(cloud);
    }
}
