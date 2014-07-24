package org.dasein.cloud.aws.quotas;

import org.apache.log4j.Logger;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.ProviderContext;
import org.dasein.cloud.ResourceStatus;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.aws.compute.EC2Exception;
import org.dasein.cloud.aws.compute.EC2Method;
import org.dasein.cloud.quotas.AbstractQuotaSupport;
import org.dasein.cloud.quotas.QuotaDescriptor;
import org.dasein.cloud.util.APITrace;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

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
    public Collection<QuotaDescriptor> getQuotas() throws CloudException, InternalException {
        APITrace.begin(getProvider(), "listVirtualMachineStatus");
        try {
            ProviderContext ctx = getProvider().getContext();

            if (ctx == null) {
                throw new CloudException("No context was established for this request");
            }
            Map<String, String> parameters = getProvider().getStandardParameters(getProvider().getContext(), EC2Method.DESCRIBE_TRUSTED_ADVISOR_CHECKS);
            parameters.put("Language", "en");
            EC2Method method = new EC2Method(getProvider(), getProvider().getEc2Url(), parameters);
            ArrayList<ResourceStatus> list = new ArrayList<ResourceStatus>();
            NodeList blocks;
            Document doc;

            try {
                doc = method.invoke();
            } catch (EC2Exception e) {
                logger.error(e.getSummary());
                throw new CloudException(e);
            }

            /*
            blocks = doc.getElementsByTagName("Checks");
            for (int i = 0; i < blocks.getLength(); i++) {
                NodeList checks = blocks.item(i).getChildNodes();

                for (int j = 0; j < checks.getLength(); j++) {
                    Node instance = checks.item(j);

                    if (instance.getNodeName().equals("item")) {
                        ResourceStatus status = toStatus(instance);

                        if (status != null) {
                            list.add(status);
                        }
                    }
                }
            }
            return list;*/

            return null;
        } finally {
            APITrace.end();
        }
    }

    public AWSCloud getProvider() {
        return provider;
    }
}
