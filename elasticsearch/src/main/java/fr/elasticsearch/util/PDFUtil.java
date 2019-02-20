package fr.elasticsearch.util;

import org.apache.logging.log4j.Logger;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

public class PDFUtil {

    private static Logger logger = LoggerUtil.getInstance();

    public String parseDocument(String document)  {
        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream;
        ParseContext pcontext = new ParseContext();

        try {
            ClassLoader classLoader = getClass().getClassLoader();
            inputstream = new FileInputStream(new File(Objects.requireNonNull(classLoader.getResource(document)).getFile()));

            //parsing the document using PDF parser
            PDFParser pdfparser = new PDFParser();
            pdfparser.parse(inputstream, handler, metadata,pcontext);
        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }

        //getting the content of the document
        logger.info("Contents of the PDF :" + handler.toString());

        //getting metadata of the document
        logger.info("Metadata of the PDF:");
        String[] metadataNames = metadata.names();

        for(String name : metadataNames) {
            logger.info(name+ " : " + metadata.get(name));
        }

        return handler.toString();
    }
}
