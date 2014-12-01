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

    private static final Logger logger = AWSCloud.getLogger(CaseSupportMethod.class);
    private static final Logger wire = AWSCloud.getWireLogger(CaseSupportMethod.class);
    private static final String URL = "https://support.us-east-1.amazonaws.com";
    private static final String CONTENT_TYPE = "application/x-amz-json-1.1";
    private static final String EMPTY_BODY = "";
    private static final String service = "support";
    private AWSCloud provider;
    private CaseSupportTarget AMZ_TARGET;

    public CaseSupportMethod( AWSCloud provider, CaseSupportTarget target ) {
        this.provider = provider;
        this.AMZ_TARGET = target;
    }

    public String invoke() throws CloudException, InternalException {
        return invoke(EMPTY_BODY);
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
            if( request_parameters == null ) {
                request_parameters = EMPTY_BODY;
            }
            httpClient = provider.getClient();

            Map<String, String> map = new HashMap<String, String>();
            map.put("Content-Type", CONTENT_TYPE);
            map.put("X-Amz-Date", provider.getV4HeaderDate(null));
            map.put("X-Amz-Target", AMZ_TARGET.getTarget());
            map.put("host", new URI(URL).getHost());

            HttpPost request = new HttpPost(URL);
            for( Entry<String, String> enumSet : map.entrySet() ) {
                request.addHeader(enumSet.getKey(), enumSet.getValue());
            }
            request.addHeader("Authorization", getAuthorizationHeader(map,request_parameters ));
            request.setEntity(getParameters(request_parameters));

            try {
                HttpResponse response = httpClient.execute(request);

                int status = response.getStatusLine().getStatusCode();
                if( status == HttpServletResponse.SC_OK ) {
                    return getContent(response.getEntity().getContent());
                }
                else if( status == HttpServletResponse.SC_SERVICE_UNAVAILABLE ||
                        status == HttpServletResponse.SC_INTERNAL_SERVER_ERROR
                        ) {

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

    private String getAuthorizationHeader( Map<String, String> paramsForHeader, String request_parameters ) throws InternalException {
        final String accessId;
        final String secret;
        try {
            accessId = new String(provider.getContext().getAccessPublic(), "utf-8");
            secret = new String(provider.getContext().getAccessPrivate(), "utf-8");
        } catch (UnsupportedEncodingException e) {
            throw new InternalException(e);
        }
        return provider.getV4Authorization(accessId, secret, "POST", URL, service, paramsForHeader, getRequestBodyHash(request_parameters));
    }

    private static StringEntity getParameters(String bodyText) throws InternalException {
        try {
            return new StringEntity(bodyText);
        } catch( UnsupportedEncodingException e ) {
            throw new InternalException(e);
        }
    }

    private static String getRequestBodyHash(String bodyText) throws InternalException {
        if( bodyText == null ) {
            return AWSCloud.computeSHA256Hash(EMPTY_BODY);
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
