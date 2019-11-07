import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConversionManager {
    public ConversionManager(){
    }

    public static List readConversions(){

        List<String> result = null;

        try (Stream<Path> walk = Files.walk(Paths.get("conversions"))) {

            result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());

            result.forEach(System.out::println);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    private void loadConversions(List result) {
        String type;
        for(Object i : result){
            File f = new File(result.toString().replace("]",""));
            type = f.getName().replace(".xml", "").replace("_", " ");
            System.out.println(type);
            try {
                // First, create a new XMLInputFactory
                XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                // Setup a new eventReader
                InputStream in = new FileInputStream(i.toString());
                XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
                // Save as a 'Conversion' object
                Conversion conversion = null;

                while (eventReader.hasNext()) {
                    XMLEvent event = eventReader.nextEvent();

                    //Checks the start of 'conversions'
                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        // If the start element is 'conversions', then we continue reading each conversion
                        if (startElement.getName().getLocalPart().equals("conversions")) {
                            continue;
                        }
                        // If the element is the start of a conversion, then we create a conversion file
                        if (startElement.getName().getLocalPart().equals("conversion")) {
                            conversion = new Conversion(type);
                            continue;
                        }

                    }
                }


            } catch (IOException e) {
                e.printStackTrace();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialise() {
        loadConversions(readConversions());
    }
}
