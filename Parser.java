import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXParseException;

public class Parser {

    private static final String FILENAME = "eurofxref-daily.xml";

    public static void main(String[] args) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder bd = dbf.newDocumentBuilder();
            Document doc = bd.parse(new File(FILENAME));
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("Cube");

            new Calculator(list);

        } catch (FileNotFoundException|NumberFormatException|SAXParseException ex) {
            popUpMessage(ex.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getClass().getCanonicalName());
        } 
    }

    public static void popUpMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.INFORMATION_MESSAGE);
    }
}
