package com.m295.booktopia.author;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;

@RestController
@Validated
public class AuthorController {
	
    private AuthorService authorService;

    AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/author")
    public ResponseEntity<List<Author>> all() {
        List<Author> result = authorService.getAuthor();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/author")
    public ResponseEntity<Author> newAuthor(@Valid @RequestBody Author author) {
        Author savedAuthor = authorService.insertAuthor(author);
        return new ResponseEntity<>(savedAuthor, HttpStatus.OK);
    }

}
