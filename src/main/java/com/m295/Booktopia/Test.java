package com.m295.Booktopia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.m295.Booktopia.Author.Author;


public class Test {
	
	@PostMapping("/post")
    public ResponseEntity<String> postData(@RequestBody String data) {
        System.out.println("Received data: " + data);
        String response = "Received data: " + data;
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@PostMapping("/author")
    public String createAuthor(@RequestBody Author author) {
    	System.out.println("Received Author: " + author.getName());
        return "Author created with name: " + author.getName() ;
    }

}
