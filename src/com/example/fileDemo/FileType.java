package com.example.fileDemo;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public interface FileType {
    void updateText(String searchString, String replaceString) throws ParserConfigurationException, IOException, SAXException, TransformerException;
}
