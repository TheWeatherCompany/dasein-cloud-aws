package org.dasein.cloud.aws.compute;

import org.apache.log4j.Logger;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.DependencyViolationException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.aws.AWSCloud;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * Utility class to perform EC2 cloud requests
 * User: mgulimonov
 * Date: 24.05.2014
 */
public class EC2Gateway {
    static private final Logger logger = Logger.getLogger(EC2Gateway.class);
    public static final String DEPENDENCY_VIOLATION_CODE = "DependencyViolation";

    private AWSCloud provider;

    public EC2Gateway(AWSCloud provider) {
        this.provider = provider;
    }

    /**
     * Sends message to AWS cloud
     * @param action action to perform
     * @param params action parameters
     * @return XML document instance
     * @throws InternalException
     * @throws CloudException
     */
    public Document invoke(String action, Map<String, String> params) throws InternalException, CloudException {
        Map<String, String> parameters = provider.getStandardParameters(provider.getContext(), action);
        parameters.putAll(params);

        try {
            EC2Method method = new EC2Method(provider, provider.getEc2Url(), parameters);
            return method.invoke();
        } catch (EC2Exception e) {
            logger.error(e.getSummary());
            if (logger.isDebugEnabled()) {
                logger.debug(e.getMessage(), e);
            }

            throw translateException(e);
        }
    }

    protected CloudException translateException(EC2Exception e) {
        if (DEPENDENCY_VIOLATION_CODE.equals(e.getCode())) {
            return new DependencyViolationException(e.getMessage());
        }

        return new CloudException(e);
    }


}
