package markwasilwa.wmmx;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class JavaXmlCustomerFilter {

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder =  factory.newDocumentBuilder();
        Document document = builder.parse("src/main/resources/continents.xml");

        DocumentTraversal trav = (DocumentTraversal) document;

        CustomFilter filter = new CustomFilter();
        NodeIterator it = trav.createNodeIterator(document.getDocumentElement(),
                NodeFilter.SHOW_ELEMENT, filter, true);

        for (Node node = it.nextNode(); node != null; node = it.nextNode()) {
            String name = node.getNodeName();
            String text = node.getTextContent().trim().replaceAll("\\s+", " ");
            System.out.printf("%s: %s%n", name, text);
        }
    }

    static class CustomFilter implements NodeFilter {

        @Override
        public short acceptNode(Node n) {
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element e = (Element) n;
                String nodeName = e.getNodeName();

                if ("slovakia".equals(nodeName) || "poland".equals(nodeName)) {
                    return NodeFilter.FILTER_ACCEPT;
                }
            }
            return NodeFilter.FILTER_REJECT;
        }
    }
}
