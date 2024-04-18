package com.m295.booktopia.book;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m295.booktopia.author.Author;
import com.m295.booktopia.author.AuthorRepository;
import com.m295.booktopia.award.Award;
import com.m295.booktopia.award.AwardRepository;
import com.m295.booktopia.genre.Genre;
import com.m295.booktopia.genre.GenreRepository;
import com.m295.booktopia.message.ResourceNotFoundException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private GenreRepository genreRepo;

    @Autowired
    private AwardRepository awardRepo;

    public List<Book> getAllBooks() {
        return bookRepo.findByOrderByNameAsc();
    }
    
    public Book getBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }

    public Book createBook(Book book) {
        // Save genre
        Author author = authorRepo.save(book.getAuthor());
        book.setAuthor(author);

        // Save genre
        Genre genre = genreRepo.save(book.getGenre());
        book.setGenre(genre);

        // Save award
        Award award = awardRepo.save(book.getAward());
        book.setAward(award);

        // Save book
        return bookRepo.save(book);
    }

    public Book updateBook(Long id, Book book) {
        Book existingBook = getBookById(id);
        // Update existingBook with the fields from book and save
        existingBook.setName(book.getName());
        existingBook.setSeries(book.getSeries());
        // Update other fields similarly
        return bookRepo.save(existingBook);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepo.delete(book);
    }
}

