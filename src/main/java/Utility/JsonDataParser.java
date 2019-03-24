package Utility;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import static Utility.GlobalObjects.readConfigurationPropertiesClassInstance;

public class JsonDataParser {
    public static String getValueFromJSONFile(String dataToBeReferred, String Fieldname) throws IOException {

        String value = "";
        readConfigurationPropertiesClassInstance.retrieve_configproperties();

        try(InputStream inputStream = new FileInputStream(ReadConfigurationProperties.apidatapath);
            JsonReader jsonReader = Json.createReader(inputStream)){
            JsonObject objJSON = jsonReader.readObject();
            JsonArray results = objJSON.getJsonArray(dataToBeReferred);
            System.out.println("data to be referred "+ dataToBeReferred);
            for (JsonObject result : results.getValuesAs(JsonObject.class)){
                value = value + result.getString(Fieldname,"");
            }

        }
        return value;
    }

    public static JSONObject getRequestObjectPositive(String dataToBeReferred, String fieldname) throws IOException, ParseException {
        String result = null;
        readConfigurationPropertiesClassInstance.retrieve_configproperties();
        try(InputStream inputStream = new FileInputStream(ReadConfigurationProperties.testpostpath);
            JsonReader jsonReader = Json.createReader(inputStream)){
            JsonObject objJsonReader = jsonReader.readObject();
            result = objJsonReader.getJsonObject(fieldname).toString();
            System.out.println(">>AKASH>"+result);


        }
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(result);

        return jsonObject;
    }




}
