package Utility;

import gherkin.lexer.Gl;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;

import javax.net.ssl.SSLContext;
import java.io.IOException;

public class Global implements GlobalObjects{

    private static String serviceURL;
    public static String responseString;
    public static String requestString;
    private static boolean isSuccess = true;
    private static HttpResponse response;
    private static HttpEntity entity;
    private static JSONObject requestObject;

    public Global(){

    }

    public static String getServiceURL(){
        return serviceURL;
    }

    public static void setServiceUrl(String url){

        Global.serviceURL = serviceURL;
    }
    public static String getRequestString(){
        return requestString;
    }
    public static void setRequestString(String requestString){
        Global.requestString = requestString;
    }

    public static HttpResponse getResponse(){
        return response;
    }
    public static void setResponse(HttpResponse response){

        Global.response = response;
    }
    public static HttpEntity getEntityValue(){
        return entity;
    }
    public static void setEntityValue(HttpEntity entity){
        Global.entity = entity;
    }
    public static void setResponseString(String responseString){
        Global.responseString = responseString;
    }

    public static String getResponseString(){
        return responseString;
    }

    public static void setRequestObject(JSONObject requestObject){
        Global.requestObject = requestObject;
    }

    public static JSONObject getRequestObject(){
        return requestObject;
    }

    public static boolean HttpClient(String requestMethod) throws Exception {

        SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,(certificate, authType) -> true).build();
        CloseableHttpClient httpClient = null;
        HttpUriRequest request =null;
        String uri = Global.getServiceURL();

        if(uri.contains("ConsentID")){
            String actualConsentId = getValueClassInstance.getValueResponse();
            uri = uri.replace("ConsentID",actualConsentId);
            Global.setServiceUrl(uri);
        }

        String requestString = Global.getRequestString();

        try{
            switch (requestMethod){
                case "POST-Header":{

                    /*String HeaderPresence = JsonDataParser
                            .getValueJsonFile(stepDefinitionClassInstance.currentDataValue,"Headers");*/
                    StringEntity requestBody = new StringEntity(requestString);

                    httpClient = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
                    request = RequestBuilder
                            .post()
                            .setUri(uri)
                            .setHeader(HttpHeaders.CONTENT_TYPE,"application/x-www-form-urlrncoded")
                            .setEntity(requestBody)
                            .build();

                    break;

                }

                case "GET-Header":{
                    httpClient = HttpClients.custom().setSSLContext(sslContext).setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
                    request = RequestBuilder
                            .get()
                            .setUri(uri)
                            .build();

                     break;
                }
                default: {
                    isSuccess = false;
                }

            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {

            if(requestMethod.contains("Header")){
                String value = "";
                if(requestMethod.contains("Header2")){
                    value = JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,"Header2");

                }
                else{
                    value = JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,"Header");
                }

                if(value.contains("TIAAToken")){

                }

                CloseableHttpResponse response = httpClient.execute(request);
                Global.setResponse(response);
                Global.setEntityValue(response.getEntity());

                if(response.getEntity()!= null){
                    Global.responseString = EntityUtils.toString(response.getEntity());
                }
                response.close();
                httpClient.close();

            }
            return isSuccess;
        }
    }
}
