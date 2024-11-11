package me.danbrown.railflow.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class XmlUtils {

    public static LocalTime nodeToLocalTime(Node node) {
        return Optional.ofNullable(node)
                .map(Node::getNodeValue)
                .map(LocalTime::parse)
                .orElse(null);
    }

    public static LocalDate nodeToLocalDate(Node node) {
        return Optional.ofNullable(node)
                .map(Node::getNodeValue)
                .map(LocalDate::parse)
                .orElse(null);
    }

    public static String nodeToString(Node node) {
        return Optional.ofNullable(node)
                .map(Node::getNodeValue)
                .orElse(null);
    }

    public static Optional<Node> findChildNode(Node node, String nodeName) {
        NodeList children = node.getChildNodes();
        Node currentNode = children.item(0);

        while (currentNode != null) {
            if (currentNode.getNodeName().equals(nodeName)) {
                return Optional.of(currentNode);
            }
            currentNode = currentNode.getNextSibling();
        }
        return Optional.empty();
    }

    public static Document constructDocument(String xml) throws IOException, SAXException, ParserConfigurationException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", false);
        factory.setNamespaceAware(false);
        DocumentBuilder builder = factory.newDocumentBuilder();
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
        return builder.parse(inputStream);
    }
}

