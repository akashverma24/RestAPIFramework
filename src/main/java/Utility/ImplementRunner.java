/***
 * ImplementRunner class is defined to read the
 * MentionFlow.xml File and fetch the values
 * related to data reference
 *
 */



package Utility;

import ApplicationRunner.AppTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImplementRunner extends AppTest {

    /***
     * executeTest method is extracting values like
     * currentTestValue, userStoryData, data ref
     * from the mention flow xml file using
     * Streamimg API for xml (Stax Parser)
     *
     *
     * @throws XMLStreamException
     * @throws XPathExpressionException
     * @throws SAXException
     * @throws ParserConfigurationException
     */



    public static void executeTests() throws XMLStreamException, XPathExpressionException, SAXException, ParserConfigurationException {

        try{
            String listofBDD = "";
            int sizeOfTestIDCount;
            String[] userStoryData;
            List<String> listOfTestID = new ArrayList<>();
            //get the list of test IDs
            listOfTestID = staxParserClassInstance.getTestIDNames();
            //get the number of TestIDs
            sizeOfTestIDCount = listOfTestID.size();

            for (int intCounter = 0; intCounter < sizeOfTestIDCount; intCounter++){
                //set the current testID globally
                currentTestIDValue = listOfTestID.get(intCounter);
                //get the list of BDDs and Data Reference for the Test ID
                userStoryData = staxParserClassInstance.getUserStoryName(listOfTestID.get(intCounter));
                runnerClassInstance.listOfUserStory = listofBDD.concat(userStoryData[0].substring(1));
                stepDefinitionClassInstance.dataRef = userStoryData[1].trim();
                //get the number of BDDs to be executed
                sizeOfUserStoryList = userStoryData.length;
            }

        }catch (IOException e){

        }

    }
}

