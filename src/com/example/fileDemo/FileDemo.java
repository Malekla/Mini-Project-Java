package com.example.fileDemo;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.InputMismatchException;


public class FileDemo {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, SAXException, IOException {

        // Handle errors in user input
        try {
            // Check if user input are not missing
            if (args.length < 3) {
                throw new InputMismatchException();
            } else {
                //Instantiate FileTypeFactory
                FileTypeFactory fileFactory = new FileTypeFactory();

                //get an object of desired file type and call its updateText method
                FileType file = fileFactory.getFileType(args[0]);
                file.updateText(args[1], args[2]);

            }

        } catch (InputMismatchException e) {
            System.out.println("ERROR: Missing input arguments.!!!!!");
            System.exit(0);
        }

    }


}
