package org.dasein.cloud.aws.platform.support;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.log4j.Logger;
import org.dasein.cloud.CloudErrorType;
import org.dasein.cloud.CloudException;
import org.dasein.cloud.InternalException;
import org.dasein.cloud.aws.AWSCloud;
import org.dasein.cloud.aws.platform.support.model.response.CaseErrorResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Eugene Yaroslavtsev
 * @since 13.08.2014
 */
public class CaseSupportMethod {

    private static final Logger logger = Logger.getLogger(CaseSupportMethod.class);
    private static final Logger wire = AWSCloud.getWireLogger(CaseSupportMethod.class);
    private static final String URL = "https://support.us-east-1.amazonaws.com";
    private static final String CONTENT_TYPE = "application/x-amz-json-1.1";
    private static final String service = "support";
    private static String bodyText = "{}";
    private AWSCloud provider;
    private CaseSupportTarget AMZ_TARGET;

    public CaseSupportMethod( AWSCloud provider, CaseSupportTarget target ) {
        this.provider = provider;
        this.AMZ_TARGET = target;
    }

    public String invoke() throws CloudException, InternalException {
        return invoke(bodyText);
    }

    public String invoke( String request_parameters ) throws CloudException, InternalException {
        if( wire.isDebugEnabled() ) {
            wire.debug("");
            wire.debug("----------------------------------------------------------------------------------");
        }
        try {
            return invoke(request_parameters, 1);
        } finally {
            if( wire.isDebugEnabled() ) {
                wire.debug("----------------------------------------------------------------------------------");
                wire.debug("");
            }
        }
    }

    private String invoke( String request_parameters, int attempts ) throws CloudException, InternalException {

        HttpClient httpClient = null;
        try {
            if( request_parameters != null ) {
                bodyText = request_parameters;
            }
            httpClient = provider.getClient();

            Map<String, String> map = new HashMap<String, String>();
            map.put("Content-Type", CONTENT_TYPE);
            String v4HeaderDate = provider.getV4HeaderDate(null);
            map.put("X-Amz-Date", v4HeaderDate);
            map.put("X-Amz-Target", AMZ_TARGET.getTarget());
            HttpPost request = new HttpPost(URL);

            for( Entry<String, String> enumSet : map.entrySet() ) {
                request.addHeader(enumSet.getKey(), enumSet.getValue());
            }
            map.put("host", new URI(URL).getHost());
            request.addHeader("Authorization", getAuthorizationHeader(map));
            request.setEntity(getParameters());

            try {
                HttpResponse response = httpClient.execute(request);

                int status = response.getStatusLine().getStatusCode();
                if( status == HttpServletResponse.SC_OK ) {
                    return getContent(response.getEntity().getContent());
                }
                else if( status == HttpServletResponse.SC_SERVICE_UNAVAILABLE || status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR ) {

                    if( attempts >= 5 ) {
                        String msg;

                        if( status == HttpServletResponse.SC_SERVICE_UNAVAILABLE ) {
                            msg = "Cloud service is currently unavailable.";
                        }
                        else {
                            msg = "The cloud service encountered a server error while processing your request.";
                        }
                        logger.error(msg);
                        throw new CloudException(msg);
                    }
                    else {
                        try {
                            Thread.sleep(5000L);
                        } catch( InterruptedException e ) {
                            // ignore me
                        }
                        return invoke(request_parameters, ++attempts);
                    }

                }
                else {
                    String code = "0"; //todo
                    String message;
                    try {
                        CaseErrorResponse caseErrorResponse = new ObjectMapper().readValue(getContent(response.getEntity().getContent()), CaseErrorResponse.class);
                        message = caseErrorResponse.getMessage();
                    } catch( CloudException e ) {
                        logger.error(e);
                        message = e.getMessage();
                    }

                    throw new CloudException(CloudErrorType.GENERAL, status, code, message);
                }

            } catch( IOException e ) {
                logger.error("I/O error from server communications: " + e.getMessage());
                throw new InternalException(e);
            }
        } catch( URISyntaxException e ) {
            logger.error(e.getMessage());
            throw new CloudException("Unable to get host from url");
        } finally {
            if( httpClient != null ) {
                httpClient.getConnectionManager().shutdown();
            }
        }
    }

    private String getAuthorizationHeader( Map<String, String> paramsForHeader ) throws InternalException {
        final String accessId;
        final String secret;
        try {
            accessId = new String(provider.getAccessKey(provider.getContext())[0], "utf-8");
            secret = new String(provider.getAccessKey(provider.getContext())[1], "utf-8");
        } catch( UnsupportedEncodingException e ) {
            logger.error(e.getMessage());
            throw new InternalException(e);
        }

        return provider.getV4Authorization(accessId, secret, "POST", URL, service, paramsForHeader, getRequestBodyHash());


    }

    private static StringEntity getParameters() throws InternalException {
        try {
            return new StringEntity(bodyText);
        } catch( UnsupportedEncodingException e ) {
            throw new InternalException(e);
        }
    }

    private static String getRequestBodyHash() throws InternalException {
        if( bodyText == null ) {
            return AWSCloud.computeSHA256Hash("{}");
        }
        else {
            return AWSCloud.computeSHA256Hash(bodyText);
        }
    }

    public static String getContent( InputStream responseBodyAsStream ) throws CloudException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(responseBodyAsStream));
            StringBuilder sb = new StringBuilder();
            String line;

            while( ( line = in.readLine() ) != null ) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch( IOException e ) {
            throw new CloudException("Unable to get content from response: " + e.getMessage(), e);
        } finally {
            if( in != null ) {
                try {
                    in.close();
                } catch( IOException e ) {
                    // Ignore
                }
            }
        }
    }
}
