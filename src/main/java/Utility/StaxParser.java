package Utility;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class StaxParser implements GlobalObjects {

    public String intGlobalTestIDCount;

    public List<String> getTestIDNames() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        readConfigurationPropertiesClassInstance.retrieve_configproperties();
        List<String> strListTestID = new ArrayList<String>();
        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc;
        String mentionFlowPathConfig = readConfigurationPropertiesClassInstance.mentionflowpath;
        StringBuilder sb = new StringBuilder();
        for (String mentionFlowPath : mentionFlowPathConfig.split(";") ){
            doc = builder.parse(mentionFlowPath);

            XPathFactory xPathFactory = XPathFactory.newInstance();
            XPath xPath = xPathFactory.newXPath();
            XPathExpression expr = xPath.compile("//class/Test[@id]");
            NodeList n1 = (NodeList) expr.evaluate(doc,XPathConstants.NODESET);

            for (int i = 0; i<n1.getLength();i++){
                Node currentItem = n1.item(i);
                String key = currentItem.getAttributes().getNamedItem("id")
                        .getNodeValue();
                strListTestID.add(key);

            }

        }
        intGlobalTestIDCount = Integer.toString(strListTestID.size());
        return strListTestID;

    }

    public  String[] getUserStoryName(String testID) throws FileNotFoundException, XMLStreamException {

        String dataRef = null;
        String listofUserStories = "";
        boolean bObjectProperty = false;
        boolean bData = false;
        String[] value = new String[2];
        String eventID = null;

        readConfigurationPropertiesClassInstance.retrieve_configproperties();

        try{
            XMLInputFactory factory = XMLInputFactory.newInstance();
            XMLEventReader eventReader;
            String mentionFLowPathConfig = readConfigurationPropertiesClassInstance.mentionflowpath;
            StringBuilder sb = new StringBuilder();

            for (String mentionFlowPath : mentionFLowPathConfig.split(";")){
                eventReader = factory.createXMLEventReader(new FileReader(new File(mentionFlowPath)));

                /*
                 *This provides iterator of events which can be used to
                 *iterate over events as they occur while parsing the XML
                 *document
                 */
                while (eventReader.hasNext()){
                    XMLEvent event = eventReader.nextEvent();
                    switch (event.getEventType()){
                        case XMLStreamConstants.START_ELEMENT:
                            StartElement startElement = event.asStartElement();
                            String qName = startElement.getName().getLocalPart();
                            if (qName.equalsIgnoreCase("Test")){
                                Iterator<Attribute> attributes = startElement.getAttributes();
                                eventID = attributes.next().getValue();
                            }
                            else if (qName.equalsIgnoreCase("Flow")){
                                bObjectProperty = true;
                            } else if (qName.equalsIgnoreCase("DataRef")){
                                bData = true;
                            }
                            break;
                            case XMLStreamConstants.CHARACTERS:
                                Characters character
                                        = event.asCharacters();
                                if (bObjectProperty){
                                    if (eventID.equals(testID)){
                                        String struserStoryName = "";
                                        while (event.isCharacters()){
                                            struserStoryName = struserStoryName
                                                    + event.asCharacters();
                                            event = eventReader.nextEvent();
                                        }
                                        listofUserStories = listofUserStories
                                                .concat(","+struserStoryName);
                                    }
                                    bObjectProperty = false;
                                }
                                if (bData){
                                    if (eventID.equals(testID)){
                                        dataRef = character.getData();
                                    }
                                    bData = false;
                                }
                                break;
                    }

                }

            }
        } catch (FileNotFoundException e){
            throw e;
        } catch (XMLStreamException e){
            throw  e;
        }
        value[0]=listofUserStories;
        value[1]=dataRef;
        return value;
    }




    public String getDataRef(String strCurrentTagName, String xmltagName) throws Exception {
        FileInputStream in = new FileInputStream("./src/main/resources/properties/config.properties");

        Properties prop = new Properties();
        prop.load(in);
        in.close();

        DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document dom;

        String apiJsonPathConfig = readConfigurationPropertiesClassInstance.apidatapath;
        String mentionFlowPathConfig = readConfigurationPropertiesClassInstance.mentionflowpath;
        StringBuilder sb = new StringBuilder();
        int counter = 0;
        for (String mentionFlowPath: mentionFlowPathConfig.split(";")){
            dom = builder.parse(mentionFlowPath);
            NodeList nList = dom.getElementsByTagName("Test");
            for (int temp = 0; temp<nList.getLength(); temp++){

                Node nNode = nList.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE){

                    Element eElement = (Element) nNode;
                    String dataRef = eElement.getElementsByTagName(xmltagName)
                            .item(0).getTextContent();
                    String Flow = eElement.getElementsByTagName("Flow").item(0).getTextContent();

                    if (Flow.equalsIgnoreCase(strCurrentTagName)){
                        readConfigurationPropertiesClassInstance.apidatapath = prop
                                .getProperty("APIDATAPATH").split(";")[counter];
                        return dataRef;
                    }
                }
            }
            counter++;
        }
        return null;
    }


}
