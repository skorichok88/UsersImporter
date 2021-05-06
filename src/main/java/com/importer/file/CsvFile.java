package com.importer.file;

import com.importer.exception.IncorrectFileTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFile {
    private static final String COMMA_DELIMITER = ",";

    private final List<List<String>> records;

    public CsvFile(MultipartFile srcFile) throws IncorrectFileTypeException, IOException {
        this.records = new ArrayList<>();
        buildCsvFileContent(srcFile);
    }

    private void buildCsvFileContent(MultipartFile file) throws IOException, IncorrectFileTypeException {
        checkCsvContentType(file);
        ByteArrayInputStream stream = new ByteArrayInputStream(file.getBytes());

        try (BufferedReader br = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(getFileDelimiter(file));
                records.add(Arrays.asList(values));
            }
        }
    }

    //TODO implement automatic file delimiter definition
    private String getFileDelimiter(MultipartFile file) {
        return COMMA_DELIMITER;
    }

    private void checkCsvContentType(MultipartFile file) throws IncorrectFileTypeException {
        if (!"text/csv".equals(file.getContentType())) {
            throw new IncorrectFileTypeException();
        }
    }

    public List<List<String>> getContent() {
        return this.records;
    }
}
