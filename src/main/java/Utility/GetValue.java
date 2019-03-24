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
