package com.m295.booktopia.book;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.m295.booktopia.security.Roles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class BookController {

    @Autowired
    private BookService bookService;

    @GetMapping("/api/book")
    @RolesAllowed({Roles.Admin, Roles.Read, Roles.Update})
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> result = bookService.getAllBooks();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    
    @GetMapping("api/book/{id}")
    @RolesAllowed({Roles.Admin, Roles.Read, Roles.Update})
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("api/book")
    @RolesAllowed({Roles.Admin})
    public ResponseEntity<Book> createBook(@RequestBody BookDto book) {
    	System.out.println(book);
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping("api/book/{id}")
    @RolesAllowed({Roles.Admin, Roles.Update})
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(book, id);
        return ResponseEntity.ok().body(updatedBook);
    }

    @DeleteMapping("api/book/{id}")
    @RolesAllowed({Roles.Admin})
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok().build();
    }
}