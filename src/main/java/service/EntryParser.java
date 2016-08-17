package service;

import model.Entry;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

class EntryParser {
    private XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    Entry parse(File file) throws IOException, XMLStreamException, ParseException {
        //TODO сделать исключения, если файл не обработать.

        FileInputStream fileInputStream = new FileInputStream(file);
        XMLEventReader reader = xmlInputFactory.createXMLEventReader(fileInputStream);

        Entry entry = null;

        try {
            while (reader.hasNext()) {
                XMLEvent event = reader.nextEvent();
                if (event.isStartElement()) {
                    StartElement startElement = event.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Entry")) {
                        entry = new Entry();
                    } else if (startElement.getName().getLocalPart().equals("content")) {
                        event = reader.nextEvent();
                        entry.setContent(event.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("creationDate")) {
                        event = reader.nextEvent();
                        entry.setCreationDate(new SimpleDateFormat("yyyy-MM-dd").parse(event.asCharacters().getData()));
                    }
                }
            }
        } finally {
            reader.close();
            fileInputStream.close();
        }
        return entry;
    }
}

