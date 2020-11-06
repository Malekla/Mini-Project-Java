package com.example.fileDemo;

import java.io.*;

public class TextFile implements FileType{
    private  String textFileUrl = "com\\example\\fileDemo\\manifesto.txt";
    private  String txtFileResult = "com\\example\\fileDemo\\result.txt";
    @Override
    public void updateText(String searchString, String replaceString) throws IOException {
        File file = new File(textFileUrl);
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
}
