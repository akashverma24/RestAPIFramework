package test;

import Utility.GlobalObjects;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Collection;

import static io.restassured.RestAssured.given;


public class StepDefinition implements GlobalObjects {

    Scenario scenario;
    public String dataRef;
    public String currentDataValue;
    public static String scenarioDescription;
    public static String userStoryName;
    public String currentTagName;


    @Before
    public void setUp(Scenario scenario) throws Exception {
        this.scenario = scenario;
        scenarioDescription = this.scenario.getName();
        Collection<String> tags = scenario.getSourceTagNames();
        for (String tag: tags){
            userStoryName = tag;
            scenarioDescription = scenario.getName();
            currentTagName = tag;

            readConfigurationPropertiesClassInstance.retrieve_configproperties();
            String dataRef = staxParserClassInstance.getDataRef(currentTagName,"DataRef");
            stepDefinitionClassInstance.dataRef = dataRef;
            stepDefinitionClassInstance.currentDataValue = dataRef;
            currentDataValue = dataRef;
            System.out.println(currentDataValue);
            stepDefinitionClassInstance.currentTagName = currentTagName.replaceFirst("@","");

            System.setProperty("CurrentTag",stepDefinitionClassInstance.currentTagName);
            readConfigurationPropertiesClassInstance.retrieve_configproperties();

        }
    }


    @Given("^I create request for \"([^\"]*)\"$")
    public void iCreateRequestFor(String serviceURL) throws Throwable {
        // Write code here that turns the phrase above into concrete actions

        stepdefinitionhelperClassInstance.createRESTRequest(serviceURL);
    }

    @And("^I send and validate request of \"([^\"]*)\"$")
    public void iSendAndValidateRequestOf(String requestMethod) throws Throwable {

        stepdefinitionhelperClassInstance.sendValidateRESTRequest(requestMethod);
    }
}
