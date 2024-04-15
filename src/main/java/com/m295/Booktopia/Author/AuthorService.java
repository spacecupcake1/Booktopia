package com.m295.booktopia.author;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
	
	private final AuthorRepository authorRepo;
	
	public  AuthorService(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findByOrderByFirstnameAscFirstnameAsc();
    }
    
    public Optional<Author> getAuthorById(Long id) {
        return authorRepo.findById(id);
    }

    public Author createAuthor(Author author) {
        return authorRepo.save(author);
    }

    public Author updateAuthor(Long id, Author updatedAuthor) {
        if (authorRepo.existsById(id)) {
            updatedAuthor.setId(id);
            return authorRepo.save(updatedAuthor);
        } else {
            throw new IllegalArgumentException("Author with id " + id + " not found");
        }
    }

    public void deleteAuthor(Long id) {
    	authorRepo.deleteById(id);
    }


}
