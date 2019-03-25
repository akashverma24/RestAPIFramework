/****
 * AppTest is a Junit Class with capabilities defined to generate Overview Report,OverChart Report,
 * SystemInfo Report in Folder "target". It acts as a Cucumber runner and a starting point of
 * the Framework.
 *
 *
 ****/

package ApplicationRunner;


import Utility.GlobalObjects;
import Utility.ImplementRunner;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumber;
import com.github.mkolisnyk.cucumber.runner.ExtendedCucumberOptions;
import cucumber.api.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;



@RunWith(ExtendedCucumber.class)
@ExtendedCucumberOptions(jsonReport = "target/cucumber.json"
        ,overviewReport = true
        ,overviewChartsReport = true
        ,systemInfoReport = true
        ,jsonUsageReport = "target/cucumber-usage.json"
        ,includeCoverageTags = {"@Test123"}
        ,outputFolder = "target"
)
@CucumberOptions(
        features = "./src/main/java/Features",
        glue = {"test"},
        plugin = {"pretty","html:test-output","json:target/cucumber.json"},
        tags = {"@Test123"},
        monochrome =true
        )
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

    public static void main(String args[]) throws Throwable {
        AppTest runnerClassInstance = new AppTest();
        ImplementRunner.executeTests();
    }

}
