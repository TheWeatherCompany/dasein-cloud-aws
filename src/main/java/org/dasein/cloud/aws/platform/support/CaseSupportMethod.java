package org.dasein.cloud.aws.platform.support;

import com.fasterxml.jackson.core.JsonProcessingException;
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

import javax.annotation.Nonnull;
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
    private static final String SERVICE = "support";
    private AWSCloud provider;
    private CaseSupportTarget AMZ_TARGET;
    private ObjectMapper objectMapper;

    public CaseSupportMethod( AWSCloud provider, CaseSupportTarget target ) {
        this.provider = provider;
        this.AMZ_TARGET = target;
        this.objectMapper = new ObjectMapper();
    }

    public <T> T invoke(Object request, Class<T> responseType) throws CloudException, InternalException {
        String rawResult = null;
        try {
            rawResult = invoke(objectMapper.writeValueAsString(request));
            return objectMapper.readValue(rawResult, responseType);
        } catch (JsonProcessingException e) {
            logger.error("Unable to prepare cloud request", e);
            throw new CloudException("Unable to process parameters" + e.getMessage());
        } catch (IOException e) {
            logger.error("Invalid cloud response: " + rawResult, e);
            throw new CloudException("Invalid cloud response: " + e.getMessage());
        }
    }

    private String invoke( @Nonnull String requestParams ) throws CloudException, InternalException {
        if( wire.isDebugEnabled() ) {
            wire.debug("");
            wire.debug("----------------------------------------------------------------------------------");
        }
        try {
            return invoke(requestParams, 1);
        } finally {
            if( wire.isDebugEnabled() ) {
                wire.debug("----------------------------------------------------------------------------------");
                wire.debug("");
            }
        }
    }

    private String invoke( String requestParams, int attempts ) throws CloudException, InternalException {

        HttpClient httpClient = null;
        try {
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
            request.addHeader("Authorization", getAuthorizationHeader(map, getRequestBodyHash(requestParams)));
            request.setEntity(getParameters(requestParams));

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
                        return invoke(requestParams, ++attempts);
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

    private String getAuthorizationHeader(Map<String, String> paramsForHeader, String requestBodyHash) throws InternalException {
        final String accessId;
        final String secret;
        try {
            accessId = new String(provider.getAccessKey(provider.getContext())[0], "utf-8");
            secret = new String(provider.getAccessKey(provider.getContext())[1], "utf-8");
        } catch( UnsupportedEncodingException e ) {
            logger.error(e.getMessage());
            throw new InternalException(e);
        }

        return provider.getV4Authorization(accessId, secret, "POST", URL, SERVICE, paramsForHeader, requestBodyHash);
    }

    private static StringEntity getParameters(String payload) throws InternalException {
        try {
            return new StringEntity(payload);
        } catch( UnsupportedEncodingException e ) {
            throw new InternalException(e);
        }
    }

    private static String getRequestBodyHash(String payload) throws InternalException {
        if( payload == null ) {
            return AWSCloud.computeSHA256Hash("{}");
        }
        else {
            return AWSCloud.computeSHA256Hash(payload);
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
