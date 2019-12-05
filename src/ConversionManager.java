import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.XMLEvent;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConversionManager {
    private ObservableList<Type> types = FXCollections.observableArrayList();

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

    private void loadConversions(List result) throws FileNotFoundException {
        String typeName;
        for (Object i : result) {
            File f = new File(i.toString().replace("]", "").replace("[",""));
            typeName = f.getName().replace(".xml", "").replace("_", " ");
            System.out.println(typeName);
            Type type = new Type(typeName);
            try {
                // First, create a new XMLInputFactory
                XMLInputFactory inputFactory = XMLInputFactory.newInstance();
                // Setup a new eventReader
                InputStream in = new FileInputStream(f);
                System.out.println("File loaded");
                XMLEventReader eventReader = inputFactory.createXMLEventReader(in);
                System.out.println("Reader created");
                Conversion conversion = null;

                // Create the list of Conversion objects

                XMLEvent event = eventReader.nextEvent();
                event = eventReader.nextEvent();

                //Checks the start of 'conversions'
                if (event.isStartElement()) {
                    System.out.println("Load started");
                    // If the start element is 'conversions', then we continue reading each conversion
                    if (event.asStartElement().getName().getLocalPart().equals("conversions")) {
                        System.out.println("Has conversions");
                    }
                    // While the end element on 'conversions' isn't met
                    while (eventReader.hasNext()) {
                        // If the element is the start of a conversion, then we create a conversion file
                        event = eventReader.nextEvent();
                        if (event.isStartElement()) {
                            if (event.asStartElement().getName().getLocalPart().equals("conversion")) {
                                event = eventReader.nextEvent();
                                conversion = new Conversion(type);
                                System.out.println("Conversion object is created");
                                continue;
                            }

                            if (event.isStartElement()) {
                                if (event.asStartElement().getName().getLocalPart().equals("title")) {
                                    conversion.setName(eventReader.nextEvent().asCharacters().getData());
                                    System.out.println(conversion.getName());
                                    continue;
                                }
                            }

                            if (event.isStartElement()) {
                                if (event.asStartElement().getName().getLocalPart().equals("multiplier")) {
                                    conversion.setMultiplier(Double.parseDouble(eventReader.nextEvent().asCharacters().getData()));
                                    System.out.println(conversion.getMultiplier());
                                    continue;
                                }
                            }

                            // Used to check if there is another title and multiplier.
                            // Some conversions will have two results from one value, like kilograms to pounds and ounces, this checks to see if it will be one of those.
                            if (event.isStartElement()) {
                                if (event.asStartElement().getName().getLocalPart().equals("title2")) {
                                    conversion.setName(conversion.getName() + " and " + eventReader.nextEvent().asCharacters().getData());
                                    System.out.println(conversion.getName());
                                    continue;
                                }
                            }

                            if (event.isStartElement()) {
                                if (event.asStartElement().getName().getLocalPart().equals("multiplier2")) {
                                    conversion.setMultiplier(Double.parseDouble(eventReader.nextEvent().asCharacters().getData()));
                                    System.out.println(conversion.getMultiplier());
                                    continue;
                                }
                            }
                        }

                        if (event.isEndElement()) {
                            EndElement endElement = event.asEndElement();
                            if (endElement.getName().getLocalPart().equals("conversion")) {
                                type.addConversion(conversion);
                                System.out.println("Conversion object is saved");
                            }
                        }
                    }
                }

                // Closes the event reader
                eventReader.close();
                System.out.println("Reader closed");

                // Closes the file
                in.close();
                System.out.println("File closed");
                types.add(type);

            } catch (IOException | XMLStreamException e) {
                e.printStackTrace();
            }
        }
    }

    public void initialise() throws FileNotFoundException {
        loadConversions(readConversions());

        for(Type x: types) {
            for (Conversion y : x.getConversions()) {
                System.out.println(y.getType().getType() + " " + y.getName() + " " + y.getMultiplier());
            }
        }
    }

    public ObservableList<Type> getTypes(){
        return this.types;
    }
}
