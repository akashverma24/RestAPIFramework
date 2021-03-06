/***
 * GlobalObject is an interface
 * all the class instances are defined here
 * and all the classes implements this class.
 */


package Utility;

import ApplicationRunner.AppTest;
import test.StepDefinition;
import test.StepDefinitionHelper;

public interface GlobalObjects {

    StepDefinitionHelper stepdefinitionhelperClassInstance = new StepDefinitionHelper();
    ReadConfigurationProperties readConfigurationPropertiesClassInstance = new ReadConfigurationProperties();
    StepDefinition stepDefinitionClassInstance = new StepDefinition();
    StaxParser staxParserClassInstance = new StaxParser();
    AppTest runnerClassInstance = new AppTest();
    JsonDataReader jsonDataReader = new JsonDataReader();
    GetValue getValueClassInstance = new GetValue();



}
