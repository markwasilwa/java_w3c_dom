package markwasilwa.wmmx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class JavaXmlDomWrite {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.newDocument();

        Element root = document.createElementNS("markwasilwa.com/wmmx/users", "users");
        document.appendChild(root);

        root.appendChild(createUser(document, "1", "Robert", "Brown", "Programmer"));
        root.appendChild(createUser(document, "2", "Pamela", "Kyle", "Writer"));
        root.appendChild(createUser(document, "3", "Peter", "Smith", "Teacher"));

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

        DOMSource source = new DOMSource(document);
        File myFile = new File("src/main/resources/new_users.xml");
        StreamResult console = new StreamResult(System.out);
        StreamResult file = new StreamResult(myFile);

        transformer.transform(source, console);
        transformer.transform(source, file);
    }

    public static Node createUser(Document doc, String id, String firstname,
                                  String lastName, String occupation) {
        Element user = doc.createElement("user");

        user.setAttribute("id", id);
        user.appendChild(createUserElement(doc, "firstname", firstname));
        user.appendChild(createUserElement(doc, "lastname", lastName));
        user.appendChild(createUserElement(doc, "occupation", occupation));
        return user;
    }

    public static Node createUserElement(Document doc, String name, String value) {
        Element node = doc.createElement(name);
        node.appendChild(doc.createTextNode(value));

        return node;
    }

}
