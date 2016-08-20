package service;

import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;

public class EntryValidator {
    private SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

    public void validate(File xsd, File xml) throws SAXException, IOException {
        Schema schema = factory.newSchema(xsd);
        Validator validator = schema.newValidator();
        validator.validate(new StreamSource(xml));
    }
}
