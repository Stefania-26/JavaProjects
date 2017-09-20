import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

class GPXTranscoder {

    private Document doc;

    GPXTranscoder(File file){
        try{
        buildGPXDocument(parseKML(file));
    }
    catch (Exception ex){
        ex.printStackTrace(System.out);
        System.err.println("Something goes wrong.");
    }
    }

    private static void setAttribute(Element element, String attrName, String attrValue){
        Document doc = element.getOwnerDocument();
        Attr attr = doc.createAttribute(attrName);
        attr.setValue(attrValue);
        element.setAttributeNode(attr);
    }

    private static Element createDownLevelElement(Document doc, String elementName, String elementContent){
        Element element = doc.createElement(elementName);
        element.appendChild(doc.createTextNode(elementContent));
        return element;
    }

    private void buildGPXDocument(Element node) throws ParserConfigurationException {
        String documentName = node.getElementsByTagName("name").item(0).getTextContent();
        Element innerFolder = (Element)node.getElementsByTagName("Folder").item(0);
        String locationCode = innerFolder.getElementsByTagName("name").item(0).getTextContent();
        NodeList placemarksList = innerFolder.getElementsByTagName("Placemark");

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        this.doc = dBuilder.newDocument();

        Element rootElement = doc.createElement("gpx");
        HashMap<String, String> attrMap = new HashMap<>();
        attrMap.put("xmlns", "http://www.topografix.com/GPX/1/1");
        attrMap.put("version", "1.1");
        attrMap.put("creator", "Self-made script");
        attrMap.put("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
        attrMap.put("xsi:schemaLocation", "http://www.topografix.com/GPX/1/1 http://www.topografix.com/GPX/1/1/gpx.xsd");
        for(Map.Entry<String, String> entry : attrMap.entrySet()) {
            setAttribute(rootElement, entry.getKey(), entry.getValue());
        }
        Element metadataElement = doc.createElement("metadata");

        Element authorElement = doc.createElement("author");
        Element authorNameElement = doc.createElement("name");
        authorNameElement.appendChild(doc.createTextNode("Stefaniia Taran"));
        authorElement.appendChild(authorNameElement);

        Element documentNameElement = doc.createElement("name");
        documentNameElement.appendChild(doc.createTextNode(documentName));

        metadataElement.appendChild(documentNameElement);
        metadataElement.appendChild(authorElement);

        Element trackInfoElement = doc.createElement("trk");
        Element trackInfoSegmentElement = doc.createElement("trkseg");
        Element locationCodeElement = doc.createElement("name");
        trackInfoElement.appendChild(locationCodeElement);
        trackInfoElement.appendChild(trackInfoSegmentElement);
        locationCodeElement.appendChild(doc.createTextNode(locationCode));

        for(int i = 0; i < placemarksList.getLength(); i++){
            Element placemark = (Element)placemarksList.item(i);
            String lat_long = placemark.getElementsByTagName("coordinates").item(0).getTextContent();
            String name = placemark.getElementsByTagName("name").item(0).getTextContent();
            String url = placemark.getElementsByTagName("description").item(0).getTextContent();

            Element trkpt = doc.createElement("trkpt");
            setAttribute(trkpt, "lat", lat_long.split(",")[0]);
            setAttribute(trkpt, "lon", lat_long.split(",")[1]);

            Element nameElement = createDownLevelElement(doc, "name", name);
            trkpt.appendChild(nameElement);

            Element locationDescriptionElement = createDownLevelElement(doc, "link", "");
            setAttribute(locationDescriptionElement, "href", url);
            trkpt.appendChild(locationDescriptionElement);

            trackInfoSegmentElement.appendChild(trkpt);
        }

        rootElement.appendChild(metadataElement);
        rootElement.appendChild(trackInfoElement);
        doc.appendChild(rootElement);
    }

     GPXTranscoder gpxFormOutput() throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        DOMSource source = new DOMSource(doc);
        StreamResult result =
                new StreamResult(new File("output.gpx"));
        transformer.transform(source, result);
        return this;
    }

    private Element parseKML(File file)throws IOException{
        Document doc;
        DocumentBuilderFactory dbFactory
                = DocumentBuilderFactory.newInstance();
        try{
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            doc = dBuilder.parse(file);
        }
        catch(Exception ex) {
            ex.printStackTrace(System.out);
            throw new IOException();
        }
        doc.getDocumentElement().normalize();
        return (Element)(doc.getElementsByTagName("Folder").item(0));
    }

    static void validateSchema(String xmlFileName, String schemaFileName) throws IOException, org.xml.sax.SAXException {
        File schemaFile = new File(schemaFileName);
        Source xmlFile = new StreamSource(new File(xmlFileName));
        SchemaFactory schemaFactory = SchemaFactory
                .newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(schemaFile);
        Validator validator = schema.newValidator();
        try {
            validator.validate(xmlFile);
            System.out.println(xmlFile.getSystemId() + " is valid");
        } catch (org.xml.sax.SAXException e) {
            System.out.println(xmlFile.getSystemId() + " is NOT valid reason:" + e);
        }
    }
}
