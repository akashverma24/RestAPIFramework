/**
 * GetValue is a utility class with method
 * getValueResponse to fetch any particular
 * value from the saved API in GenerateAPIResponse
 * json File using the key tag value
 *
 */



package Utility;

import org.json.JSONObject;

import java.io.IOException;

public class GetValue extends Global {

    public String getValueResponse() throws IOException {
        JSONObject jsonObject = new JSONObject(jsonDataReader.getFileData("./src/main/resources/testdata/GenerateAPIResponse.json"));
        String consentID = jsonObject.getJSONObject("Data").getString("ConsentID");
        return consentID;
    }
}
