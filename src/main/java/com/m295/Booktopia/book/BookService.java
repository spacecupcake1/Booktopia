package com.m295.booktopia.book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m295.booktopia.author.Author;
import com.m295.booktopia.author.AuthorRepository;
import com.m295.booktopia.author.AuthorService;
import com.m295.booktopia.award.Award;
import com.m295.booktopia.award.AwardRepository;
import com.m295.booktopia.award.AwardService;
import com.m295.booktopia.genre.Genre;
import com.m295.booktopia.genre.GenreRepository;
import com.m295.booktopia.genre.GenreService;
import com.m295.booktopia.message.ResourceNotFoundException;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;
    
    @Autowired
    private AuthorService authorService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AwardService awardService;

    @Autowired
    public BookService(BookRepository bookRepo) {
        this.bookRepo = bookRepo;
        ////this.genreRepo = genreRepo;
        //this.awardRepo = awardRepo;
    }

	public List<Book> getAllBooks() {
        return bookRepo.findByOrderByNameAsc();
    }
    
    public Book getBookById(Long id) {
        return bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id " + id));
    }
    
    public Book createBook(BookDto bookDto) {
    	Optional<Author> author =authorService.getAuthorById(bookDto.getAuthorId());
    	Optional<Award> award =awardService.getAwardById(bookDto.getAwardId());
    	Optional<Genre> genre =genreService.getGenreById(bookDto.getGenreId());
    	Book book = new Book(
    			bookDto.getId(),
    			bookDto.getName(),
    			bookDto.getSeries(),
    			bookDto.getPage(),
    			bookDto.getReleaseDate(),
    			bookDto.getDescription(),
    			author.get(),
    			genre.get(),
    			award.get()
    			);   	
        return bookRepo.save(book);
    }

    public Book updateBook(Book book, Long id) {
        return bookRepo.findById(id)
                .map(existingBook -> {
                    existingBook.setName(book.getName());
                    existingBook.setSeries(book.getSeries());
                    existingBook.setPage(book.getPage());
                    existingBook.setReleaseDate(book.getReleaseDate());
                    existingBook.setDescription(book.getDescription());
                    existingBook.setAuthor(book.getAuthor());
                    existingBook.setAward(book.getAward());
                    existingBook.setGenres(book.getGenres());
                    return bookRepo.save(existingBook);
                })
                .orElseGet(() ->
                bookRepo.save(book));
    }



	/*
	 * public Book createBook(Book book) { // Save genre Author author =
	 * authorRepo.save(book.getAuthor()); book.setAuthor(author);
	 * 
	 * // Save genre List<Genre> genres = new ArrayList<>(); for (Genre genre :
	 * book.getGenres()) { genres.add(genreRepo.save(genre)); }
	 * book.setGenres(genres);
	 * 
	 * // Save award Award award = awardRepo.save(book.getAward());
	 * book.setAward(award);
	 * 
	 * // Save book return bookRepo.save(book); }
	 * 
	 * public Book updateBook(Long id, Book book) { Book existingBook =
	 * getBookById(id);
	 * 
	 * // Update existingBook with the fields from book
	 * existingBook.setName(book.getName());
	 * existingBook.setSeries(book.getSeries());
	 * existingBook.setPage(book.getPage());
	 * existingBook.setReleaseDate(book.getReleaseDate());
	 * existingBook.setDescription(book.getDescription());
	 * 
	 * // Update author Author author = authorRepo.save(book.getAuthor());
	 * existingBook.setAuthor(author);
	 * 
	 * // Update genre List<Genre> genre = genreRepo.saveAll(book.getGenres());
	 * existingBook.setGenres(genre);
	 * 
	 * // Update award Award award = awardRepo.save(book.getAward());
	 * existingBook.setAward(award);
	 * 
	 * // Save and return the updated book return bookRepo.save(existingBook); }
	 */

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepo.delete(book);
    }
}

