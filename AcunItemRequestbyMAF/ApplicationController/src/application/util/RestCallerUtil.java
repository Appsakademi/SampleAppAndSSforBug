package application.util;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.util.Base64;
import java.util.logging.Level;

import javax.microedition.io.HttpConnection;

import oracle.adfmf.util.logging.Trace;

import oracle.maf.api.dc.ws.rest.RestServiceAdapter;
import oracle.maf.api.dc.ws.rest.RestServiceAdapterFactory;

public class RestCallerUtil {
    public RestCallerUtil() {
        super();
    }

    //GET
    public String invokeREAD(String requestURI) {
        return this.invokeRestRequest(RestServiceAdapter.REQUEST_TYPE_GET, requestURI, "");
    }

    //POST
    public String invokeUPDATE(String requestURI, String payload) {

        return this.invokeRestRequest("PATCH", requestURI, payload);
    }

    //PUT
    public String invokeCREATE(String requestURI, String payload) {
        return this.invokeRestRequest(RestServiceAdapter.REQUEST_TYPE_POST, requestURI, payload);
    }


    //DELETE
    public String invokeDELETE(String requestURI) {
        return this.invokeRestRequest("DELETE", requestURI, "");
    }

    /**
     * Method that handles the boilerplate code for obtaining and configuring a service
     * adapter instance.
     *
     * @param httpMethod GET, POST, PUT, DELETE
     * @param requestURI the URI to appends to the base REST URL. URIs are expected to start with "/"
     * @return
     */
    private String invokeRestRequest(String httpMethod, String requestURI, String payload) {

        String restPayload = "";

        RestServiceAdapter restServiceAdapter = RestServiceAdapterFactory.newFactory().createRestServiceAdapter();
        restServiceAdapter.clearRequestProperties();
        //set URL connection defined for this sample. In this sample, the "hrresrconn" connection resolves
        //to http://127.0.0.1:7101/hrrest/resources/hrappsrvc . The connection has been created for this
        //sample choosing File | New | From Gallery | General | Connections | URL connection from the JDeveloper menu
        restServiceAdapter.setConnectionName("RESTConn");

        //set GET, POST, DELETE, PUT
        restServiceAdapter.setRequestMethod(httpMethod);

        //this sample uses JSON only. Thus the media type can be hard-coded in this class
        //the content-type tells the server what format the incoming payload has
        if (httpMethod.equals("GET") && !payload.isEmpty()) {
            restServiceAdapter.addRequestProperty("Content-Type", "application/vnd.oracle.adf.resourceitem+json");
        } else if (httpMethod.equals("GET") && payload.isEmpty()) {
            restServiceAdapter.addRequestProperty("Content-Type", "application/vnd.oracle.adf.resourcecollection+json");
        } else {
            restServiceAdapter.addRequestProperty("Content-Type", "application/vnd.oracle.adf.resourceitem+json");
        }
        //the accept header indicates the expected payload fromat to the server
        restServiceAdapter.addRequestProperty("Accept", "application/json; charset=UTF-8");
        restServiceAdapter.setRequestURI(requestURI);
        restServiceAdapter.setRetryLimit(2);

        //variable holding the response
        String response = "";

        //set payload if there is payload passed with the request
        if (!payload.isEmpty()) {
            //send with empty payload
            restPayload = payload;
        }
        try {
            response = restServiceAdapter.send(restPayload);
        } catch (Exception e) {
            //log error
            String a = e.getMessage();
            Trace.log("REST_JSON", Level.SEVERE, this.getClass(), "invokeRestRequest",
                      "Invoke of REST Resource failed for " + httpMethod + " to " + requestURI);
            Trace.log("REST_JSON", Level.SEVERE, this.getClass(), "invokeRestRequest", e.getLocalizedMessage());
        }
        return response;
    };
}
