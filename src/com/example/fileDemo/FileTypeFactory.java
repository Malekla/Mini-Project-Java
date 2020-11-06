package com.example.fileDemo;

public class FileTypeFactory {

    public FileType getFileType(String fileType) {
        if (fileType.equals("txt")) {
            return new TextFile();
        } else if (fileType.equals("xml")) {
            return new XmlFile();
        }
        return null;
    }
}
