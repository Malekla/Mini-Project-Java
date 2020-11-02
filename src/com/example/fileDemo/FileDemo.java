package com.example.fileDemo;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.InputMismatchException;


public class FileDemo {

    private static String textFileUrl = "com\\example\\fileDemo\\manifesto.txt";
    private static String xmlFileUrl = "com\\example\\fileDemo\\configuration.xml";
    private static String xmlFileResult = "com\\example\\fileDemo\\result.xml";
    private static String txtFileResult = "com\\example\\fileDemo\\result.txt";

    public static void main(String[] args) throws ParserConfigurationException, IOException, TransformerException, SAXException {

        // Handle errors in user input
        try {
            // Check if user input are not missing
            if (args.length < 3) {
                throw new InputMismatchException();
            } else {
                // According to file type proceed update file content
                if (args[0].equals("xml")) {
                    updateXmlFile(xmlFileUrl, args[1], args[2]);
                } else {
                    updateTextFile(textFileUrl, args[1], args[2]);
                }
            }

        } catch (InputMismatchException e) {
            System.out.println("ERROR: Missing input arguments.");
            System.exit(0);
        }

    }

    // Update XML file
    private static void updateXmlFile(String fileUrl, String searchString, String replaceString) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File file = new File(fileUrl);

        //Get a new instance of DocumentBuilderFactory
        // Defines a factory API that enables applications to obtain a parser that produces DOM object trees from XML documents
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        //Build a new Document from file
        Document document = builder.parse(file);


        //Normalize the XML Structure (removes empty nodes, etc.)
        document.getDocumentElement().normalize();

        //Get document root node
        Element rootNode = document.getDocumentElement();

        // Get the root elements
        NodeList nList = document.getElementsByTagName(rootNode.getNodeName());
        // Go through child nodes
        visitChildNodes(nList, searchString, replaceString);

        // instantiate TransformerFactory
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        // Create a new Transformer that performs a copy of the Source to the Result
        Transformer transformer = transformerFactory.newTransformer();
        // Create a new input source with a DOM nodes from document
        DOMSource source = new DOMSource(document);
        // instantiate StreamResult to hold the transformation result
        StreamResult result = new StreamResult(new File(xmlFileResult));
        // Transform the XML source to result file
        transformer.transform(source, result);

    }

    // Update TEXT file
    private static void updateTextFile(String fileUrl, String searchString, String replaceString) throws IOException {
        File file = new File(fileUrl);
        // Instantiate BufferedReader class to read file
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        String oldContent = "";
        while (line != null) {
            oldContent = oldContent + line + System.lineSeparator();
            line = reader.readLine();
        }
        if (oldContent.contains(searchString)) {
            String newContent = oldContent.replaceAll(searchString, replaceString);
            // Instantiate FileWriter class to write content in file
            FileWriter writer = new FileWriter(txtFileResult);
            System.out.println("New text content" + newContent);
            writer.write(newContent);
            writer.close();
        } else {
            System.out.println("Searched text doesnt exist");
        }
        reader.close();

    }

    //This function is called recursively
    private static void visitChildNodes(NodeList nList, String searchString, String replaceString) {
        for (int index = 0; index < nList.getLength(); index++) {
            Node node = nList.item(index);
            //Check node type
            if (node.getNodeType() == Node.ELEMENT_NODE) {

                //Check if node has attributes or child nodes
                if (node.hasAttributes() || node.hasChildNodes()) {
                    // Get attributes names and values of current node
                    NamedNodeMap nodeMap = node.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++) {
                        Node tempNode = nodeMap.item(i);
                        // Check if attribute value contains searched string
                        if (tempNode.getNodeValue().contains(searchString)) {
                            // Replace attribute value with new string
                            tempNode.setNodeValue(tempNode.getNodeValue().replaceAll(searchString, replaceString));
                        }
                        System.out.println("Attribute name : " + tempNode.getNodeName() + "; Value = " + tempNode.getNodeValue());
                    }

                    if (node.hasChildNodes()) {
                        //If current node has child nodes, repeat the same process
                        visitChildNodes(node.getChildNodes(), searchString, replaceString);
                    }
                }
            }
        }
    }

}
