package com.importer.web;

import com.importer.file.CsvFile;
import com.importer.domain.ImportResult;
import com.importer.domain.User;
import com.importer.domain.UsersService;
import com.importer.exception.IncorrectFileTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@Controller()
@RequestMapping("/users")
public class UsersImportController {
    @Autowired
    private UsersService usersService;

    @PostMapping
    public ResponseEntity<ImportResult> importUsers(@RequestParam("file") MultipartFile file) throws IOException, IncorrectFileTypeException {
        CsvFile csvFile = new CsvFile(file);
        return new ResponseEntity<>(usersService.importUsers(csvFile.getContent()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<Collection<User>> getAllUsers() throws IOException, ClassNotFoundException {
        return new ResponseEntity<>(usersService.getAllUsers().values(), HttpStatus.OK);
    }
}
