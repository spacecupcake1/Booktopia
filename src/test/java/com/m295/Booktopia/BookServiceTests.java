package com.m295.Booktopia;

import com.m295.booktopia.author.Author;
import com.m295.booktopia.author.AuthorRepository;
import com.m295.booktopia.author.AuthorService;
import com.m295.booktopia.award.Award;
import com.m295.booktopia.award.AwardRepository;
import com.m295.booktopia.award.AwardService;
import com.m295.booktopia.book.Book;
import com.m295.booktopia.book.BookRepository;
import com.m295.booktopia.book.BookService;
import com.m295.booktopia.genre.Genre;
import com.m295.booktopia.genre.GenreRepository;
import com.m295.booktopia.genre.GenreService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookServiceTests {
	
	 private BookService bookService;
	    private AuthorService authorService;
	    private GenreService genreService;
	    private AwardService awardService;

	    @Mock
	    private BookRepository bookRepoMock;

	    @Mock
	    private AuthorRepository authorRepoMock;

	    @Mock
	    private GenreRepository genreRepoMock;

	    @Mock
	    private AwardRepository awardRepoMock;
	    
	    private final Book bookMock = mock(Book.class);

	    @BeforeEach
	    void setUp() {
	        MockitoAnnotations.openMocks(this); // Initialize annotated mocks
	        bookService = new BookService(bookRepoMock, authorRepoMock, genreRepoMock, awardRepoMock);
	        authorService = new AuthorService(authorRepoMock);
	        genreService = new GenreService(genreRepoMock);
	        awardService = new AwardService(awardRepoMock);
	    }

	    @Test
	    @Order(value = 1)
	    void createBook() {
	        // Mocking behavior for the repositories
	        when(authorRepoMock.save(any())).thenReturn(mock(Author.class));
	        when(genreRepoMock.save(any())).thenReturn(mock(Genre.class));
	        when(awardRepoMock.save(any())).thenReturn(mock(Award.class));
	        when(bookRepoMock.save(any())).thenReturn(mock(Book.class));

	        // Invoke the service method
	        bookService.createBook(mock(Book.class));

	        // Verify that the save method is called exactly once
	        verify(bookRepoMock, times(1)).save(any());
	    }
	    
	    
	   @Test
	   @Order(value = 2)
	   void findBook() {
	       when(bookRepoMock.findById(any())).thenReturn(Optional.ofNullable(bookMock));
	       Book book = bookService.getBookById(any());
	       verify(bookRepoMock, times(1)).findById(any());
	   }
	   
	   
	   @Test
	   @Order(value = 3)
	   void deleteBook() {
		    when(bookRepoMock.findById(any())).thenReturn(Optional.ofNullable(bookMock));
		    bookService.deleteBook(anyLong());
		    verify(bookRepoMock, times(1)).delete(any());
		}

}
