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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
		MockitoAnnotations.openMocks(this);
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
	void findBookById() {
		when(bookRepoMock.findById(any())).thenReturn(Optional.ofNullable(bookMock));
		Book book = bookService.getBookById(any());
		verify(bookRepoMock, times(1)).findById(any());
	}

	@Test
	@Order(value = 3)
	void UpdateBook() {
        // Create a mock book with a random ID
        Book mockBook = mock(Book.class);
        when(mockBook.getId()).thenReturn(1L);

        // Mock behavior for bookRepoMock to return the mock book
        when(bookRepoMock.findById(anyLong())).thenReturn(Optional.of(mockBook));

        // Create new mock objects for author, genre, award, and book
        Author mockAuthor = mock(Author.class);
        Genre mockGenre = mock(Genre.class);
        Award mockAward = mock(Award.class);
        Book updatedBook = mock(Book.class);

        // Mock behavior for the repository save methods
        when(authorRepoMock.save(any(Author.class))).thenReturn(mockAuthor);
        when(genreRepoMock.save(any(Genre.class))).thenReturn(mockGenre);
        when(awardRepoMock.save(any(Award.class))).thenReturn(mockAward);
        when(bookRepoMock.save(any(Book.class))).thenReturn(updatedBook);

        // Invoke the updateBook method
        Book result = bookService.updateBook(1L, updatedBook);

        // Verify that the findById method is called once with the correct ID
        verify(bookRepoMock, times(1)).findById(1L);
        // Verify that the save method is called once with the updated book
        verify(bookRepoMock, times(1)).save(updatedBook);

        // Assert that the returned book is the same as the updated book
        assertEquals(updatedBook, result);
    }

	@Test
	@Order(value = 4)
	void getAllBooks() {
		// Mocking behavior for the repository
		List<Book> books = new ArrayList<>();
		books.add(new Book());
		books.add(new Book());
		when(bookRepoMock.findByOrderByNameAsc()).thenReturn(books);
		List<Book> result = bookService.getAllBooks();

		verify(bookRepoMock, times(1)).findByOrderByNameAsc();
		assert result != null;
		assert result.size() == 2;
	}

	@Test
	@Order(value = 5)
	void deleteBook() {
		when(bookRepoMock.findById(any())).thenReturn(Optional.ofNullable(bookMock));
		bookService.deleteBook(anyLong());
		verify(bookRepoMock, times(1)).delete(any());
	}

}
