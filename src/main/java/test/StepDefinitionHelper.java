package test;

import Utility.Global;
import Utility.GlobalObjects;
import Utility.JsonDataParser;
import org.json.simple.JSONObject;


import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import static io.restassured.RestAssured.given;

public class StepDefinitionHelper implements GlobalObjects {

    public void createRESTRequest(String serviveURL) throws IOException {

        Global.setServiceUrl(JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,serviveURL));

    }

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

    public void sendValidateRESTRequest(String requestMethod) throws Exception{
        JSONObject jsonObject = null;
        boolean isSuccess = false;
        String serviceURL = "";
        String requestMethodValueFromJsonFile = JsonDataParser
                .getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,requestMethod);

        if(requestMethod.contains("GET")){
            serviceURL = JsonDataParser.getValueFromJSONFile(
                    stepDefinitionClassInstance.currentDataValue,requestMethod);
            Global.setServiceUrl(serviceURL);
            isSuccess = Global.HttpClient(requestMethodValueFromJsonFile);

        }
        else{
            serviceURL = JsonDataParser.getValueFromJSONFile(
                    stepDefinitionClassInstance.currentDataValue,"ServiceURL");
            Global.setServiceUrl(serviceURL);


            if (serviceURL.contains("ConsentID")){

            }

            String reqFormat = JsonDataParser.getValueFromJSONFile(stepDefinitionClassInstance.currentDataValue,"RequestFormat");
            
            JSONObject jsonObject1 = JsonDataParser.getRequestObjectPositive(stepDefinitionClassInstance.currentDataValue,reqFormat);
            Global.setRequestObject(jsonObject1);
            Global.setRequestString(Global.getRequestObject().toString());
            String str = Global.getRequestString();
            

            isSuccess = Global.HttpClient(requestMethodValueFromJsonFile);
            

        }


    }



}
