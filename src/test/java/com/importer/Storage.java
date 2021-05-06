package com.importer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class Storage {
    @Value("${localfilestorage.filepath}")
    private String dataFilePath;

    @Value("${localfilestorage.datadirectory}")
    private String dataDirectory;

    public void cleanupUsersStorage() {
        String dataFileFullPath = System.getProperty("user.home") + dataFilePath;
        String dataDirectoryFullPath = System.getProperty("user.home") + dataDirectory;
        try {
            Files.deleteIfExists(Paths.get(dataFileFullPath));
            Files.deleteIfExists(Paths.get(dataDirectoryFullPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
