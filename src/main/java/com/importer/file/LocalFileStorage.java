package com.importer.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Component
public class LocalFileStorage implements FileStorage {
    @Value("${localfilestorage.filepath}")
    private String dataFilePath;

    @Value("${localfilestorage.datadirectory}")
    private String dataDirectory;

    public Object getStorageData() {
        createDataDirectoryIfNotExist();
        File file = new File(System.getProperty("user.home") + dataFilePath);
        ObjectInputStream input = null;
        Object fileData;
        try {
            input = new ObjectInputStream(new GZIPInputStream(new FileInputStream(file)));
            fileData = input.readObject();
            input.close();
        } catch (FileNotFoundException e) {
            return null;
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return fileData;
    }

    public void saveDataToStorage(Object data) {
        File file = new File(System.getProperty("user.home") + dataFilePath);
        ObjectOutputStream output;
        try {
            output = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream(file)));
            output.writeObject(data);
            output.flush();
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createDataDirectoryIfNotExist() {
        File directory = new File(System.getProperty("user.home") + dataDirectory);
        if (! directory.exists()){
            directory.mkdir();
        }
    }
}
