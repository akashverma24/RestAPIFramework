/**
 * StepDefinition class is a module to help StepDefinition
 * class and different Utility package to interact
 * with each other, acts as a helper.
 */


package test;

import Utility.Global;
import Utility.GlobalObjects;
import Utility.JsonDataParser;
import org.json.simple.JSONObject;
import java.io.FileWriter;
import java.io.IOException;



public class StepDefinitionHelper implements GlobalObjects {

    /**
     * createRESTRequest method is parsing Endpoint/URI
     * from APIData json file using JsonDataParser class from Utility package
     * and setting the serviceURL Globally in Global class.
     * It is preparing the data for executing REST Request
     * @param serviceURL
     * @throws IOException
     */


    public void createRESTRequest(String serviceURL) throws IOException {

        Global.setServiceUrl(JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,serviceURL));
        System.out.println("test  "+JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,serviceURL));
        System.out.println(Global.getServiceURL());
    }

    /***
     * Method to save the response of current API
     * to GenerateAPIResponse.json file so that
     * validation tags can be used further into
     * different API.
     * @throws IOException
     */

    public void saveAPIRes() throws IOException {

        String res = Global.getResponseString();

        try{
            FileWriter file = new FileWriter("./src/main/resources/testdata/GenerateAPIResponse.json");
            file.write(res);
            file.flush();
            file.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /***
     * sendValidateRESTRequest method is collecting all the values
     * like serviveURL,Request Methods, Request Payload in case
     * of POST from the globally set values.It checks the Request
     * method and then executes the API using HTTP Client
     * which is defined in Global Class.
     * @param requestMethod
     * @throws Exception
     *
     *
     */


    public void sendValidateRESTRequest(String requestMethod) throws Exception{
        JSONObject jsonObject = null;
        boolean isSuccess = false;
        String serviceURL = "";
        String requestMethodValueFromJsonFile = JsonDataParser
                .getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,requestMethod);
        System.out.println("::::Validation>>API Request Method Extracted from the API Data Json File::::"+requestMethodValueFromJsonFile);

        if(requestMethodValueFromJsonFile.contains("GET")){
            serviceURL = JsonDataParser.getValueFromJSONFile(
                    stepDefinitionClassInstance.currentDataValue,"ServiceURL");
            System.out.println(":::::Validation>>GET Request API Service URL From the API Data Json File:::::"+serviceURL);
            Global.setServiceUrl(serviceURL);
            isSuccess = Global.HttpClient(requestMethodValueFromJsonFile);

        }
        else{
            serviceURL = JsonDataParser.getValueFromJSONFile(
                    stepDefinitionClassInstance.currentDataValue,"ServiceURL");
            Global.setServiceUrl(serviceURL);

            System.out.println(":::::Validation>>POST Request API Service URL From the API Data Json File:::::"+Global.getServiceURL());

            if (serviceURL.contains("ConsentID")){
                String actualconsetID = getValueClassInstance.getValueResponse();
                serviceURL = serviceURL.replace("ConsentID",actualconsetID);
                Global.setServiceUrl(serviceURL);

            }

            String reqFormat = JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,"RequestFormat");
            System.out.println("::::Validation>> Extract the Data Reference for API Request Payload from Test Post Json File"+reqFormat);
            JSONObject jsonObject1 = JsonDataParser.getRequestObjectPositive(stepDefinitionClassInstance.currentDataValue,reqFormat);
            Global.setRequestObject(jsonObject1);
            Global.setRequestString(Global.getRequestObject().toString());
            String str = Global.getRequestString();
            System.out.println(":::::Validation>> Set the payload String as global and convert JsonObject to String"+str);

            isSuccess = Global.HttpClient(requestMethodValueFromJsonFile);
            System.out.println("::::::::::Validation>>Status of the API result:::::::::"+isSuccess);


        }


    }



}
