/*****
 * StepDefinition class consist of inner binding
 * of Cucumber Gherkin steps and java logic to
 * provide logical meaning defined in the
 * TestAPI.feature
 */


package test;

import Utility.GlobalObjects;
import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import java.util.Collection;



public class StepDefinition implements GlobalObjects {

    Scenario scenario;
    public String dataRef;
    public String currentDataValue;
    public static String scenarioDescription;
    public static String userStoryName;
    public String currentTagName;

    /***
     * setUp method is extracting the annotation values from the feature file.
     * Annotation values such as Scenario, Scenario Description, UserStoryName,
     * CurrentTagName, DataRef and setting them globally for use in step definition.
     * @param scenario
     * @throws Exception
     */

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
