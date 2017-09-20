import org.xml.sax.SAXException;

import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        File kml = new File("M-34-108-points.kml");
        GPXTranscoder gpxTranscoder = new GPXTranscoder(kml);
        try{
            gpxTranscoder.gpxFormOutput();
        }
        catch (TransformerException e){
            System.err.println("During transformation happened an error. Please restart.");
        }
        try {
            GPXTranscoder.validateSchema("output.gpx", "gpx-schema.xsd");
        } catch (IOException e) {
            System.err.println("Your file is a mess either not existent. Try something else.");
        } catch (SAXException e) {
            System.err.println("Your file is not valid, boy. Try something else.");
        }
    }
}