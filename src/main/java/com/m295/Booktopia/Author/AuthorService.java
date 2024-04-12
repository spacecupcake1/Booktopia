package com.m295.booktopia.author;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

import java.util.List;

@Service
public class AuthorService {
	
	private final AuthorRepository repo;
	
	public  AuthorService(AuthorRepository repo) {
        this.repo = repo;
    }

    public List<Author> getAuthor() {
        return repo.findByOrderByNameAscFirstnameAsc();
    }
    
    public Author insertAuthor(Author author) {
        return repo.save(author);
    }


}
