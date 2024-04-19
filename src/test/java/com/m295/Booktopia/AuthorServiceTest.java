package com.m295.Booktopia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.m295.booktopia.author.Author;
import com.m295.booktopia.author.AuthorRepository;
import com.m295.booktopia.author.AuthorService;

public class AuthorServiceTest {
	private AuthorService authorService;
	private final AuthorRepository authorRepositoryMock = mock(AuthorRepository.class);

	private final Author authorMock = mock(Author.class);

	@BeforeEach
	void setUp() {
		authorService = new AuthorService(authorRepositoryMock);
	}

	@Test
	void createAuthor() {
		when(authorRepositoryMock.save(authorMock)).thenReturn(authorMock);
		authorService.createAuthor(authorMock);
		verify(authorRepositoryMock, times(1)).save(any());
	}

	@Test
	void findAuthorById() {
		when(authorRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(authorMock));
		Optional<Author> a = authorService.getAuthorById(any());
		verify(authorRepositoryMock, times(1)).findById(any());
	}

	@Test
	void findAllAuthors() {
		List<Author> authorList = new ArrayList<>();
		authorList.add(authorMock);

		when(authorRepositoryMock.findAll()).thenReturn(authorList);

		List<Author> result = authorService.getAllAuthors();

		assertEquals(authorList, result);
		verify(authorRepositoryMock, times(1)).findAll();
	}

	@Test
	void updateAuthor() {

		Long id = 1L;
		Author mockAuthor = new Author();

		when(authorRepositoryMock.findById(id)).thenReturn(Optional.of(mockAuthor));
		when(authorRepositoryMock.save(mockAuthor)).thenReturn(mockAuthor);
		Author updatedAuthor = new Author();

		updatedAuthor.setFirstname("First");
		updatedAuthor.setLastname("Last");
		updatedAuthor.setBirthdate(LocalDate.of(1980, 5, 15));


		Author result = authorService.updateAuthor(id, updatedAuthor);
		assertEquals(updatedAuthor.getFirstname(), result.getFirstname());
		assertEquals(updatedAuthor.getLastname(), result.getLastname());

	}

	@Test
	void deleteAuthor() {
		authorService.deleteAuthor(any());
		verify(authorRepositoryMock, times(1)).deleteById(any());
	}
}
