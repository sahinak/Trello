package com.trello.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;

import com.sun.jersey.client.urlconnection.URLConnectionClientHandler;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.json.JSONObject;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.client.urlconnection.HTTPSProperties;


public class GenericRESTHandler {
    
    public enum HTTPMethod {
        GET, POST, PUT, DELETE, PATCH
    }

    private WebResource wResource;
    private String user_cred;
    private String base64;
    private String response_string;
    private int status;
    private ClientResponse response;
    private Map<String, String> apiResponseString;
  
   
    private ClientConfig besicClientConfig = null;
  


    public Map<String, String> ExecuteAPI(HTTPMethod methodType, String URI, String uname, String pwd, Object payload,
                                          String responseType, String fileName) throws UniformInterfaceException, UnsupportedEncodingException {

     

        // getting the client config
        ClientConfig config = getBasicClientConfig();
        final Client client = Client.create(config);

        wResource = client.resource(URI);

        switch (methodType) {
            case GET: {
                response = wResource.accept(MediaType.APPLICATION_JSON)
                         .get(ClientResponse.class);
                apiResponseString = APIResponseProvider.getResponse(response, responseType, null);
                break;
            }
            case POST: {
                response = wResource.accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class);
                apiResponseString = APIResponseProvider.getResponse(response, responseType, fileName);
                break;
            }
            
            case DELETE: {
                response = wResource.accept(MediaType.APPLICATION_JSON)
                        .delete(ClientResponse.class);
                apiResponseString = APIResponseProvider.getResponse(response, responseType, null);
                break;

            }
          
        }
        return apiResponseString;
    }

    public Map<String, String> ExecuteAPI(HTTPMethod methodType, String URI, String uname, String pwd, Object payload)
            throws UniformInterfaceException, UnsupportedEncodingException {
        return ExecuteAPI(methodType, URI, uname, pwd, payload, "json", null);
    }

    
   
    private static class SSLUtil {
        protected static SSLContext getInsecureSSLContext() throws KeyManagementException, NoSuchAlgorithmException {
            final TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(final java.security.cert.X509Certificate[] arg0, final String arg1)
                        throws CertificateException {
                    // do nothing and blindly accept the certificate
                }

                public void checkServerTrusted(final java.security.cert.X509Certificate[] arg0, final String arg1)
                        throws CertificateException {
                    // do nothing and blindly accept the server
                }

            }};

            final SSLContext sslcontext = SSLContext.getInstance("SSL");
            sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
            return sslcontext;
        }
    }
   

   

    private ClientConfig getBasicClientConfig() {
        if (besicClientConfig == null) {
            final ClientConfig config = new DefaultClientConfig();
            try {
                 config.getProperties().put(HTTPSProperties.PROPERTY_HTTPS_PROPERTIES, new HTTPSProperties(
                        SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER, SSLUtil.getInsecureSSLContext()));
            } catch (KeyManagementException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return config;
        } else {
            return besicClientConfig;
        }
    }

   
}
