package ApplicationRunner;


import Utility.GlobalObjects;
import Utility.ImplementRunner;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "./src/main/java/Features",
        glue = {"test"},
        plugin = {"pretty"})
        //tags = {"@Test1"},
        //monochrome = true
        //"usage:target/cucumber-usage.json",
        //"junit:targer/cucumber-results.xml"

public class AppTest implements GlobalObjects {

    public String listOfUserStory = null;
    public static int sizeOfUserStoryList;
    public static String currentTestIDValue;

    @BeforeClass
    public static void setup(){

    }

    public static void main(String args[]) throws SAXException, ParserConfigurationException, XPathExpressionException, XMLStreamException {
        AppTest runnerClassInstance = new AppTest();
        ImplementRunner.executeTests();
    }

}
