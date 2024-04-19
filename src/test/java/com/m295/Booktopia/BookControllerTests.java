package com.m295.Booktopia;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;



import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.client.RestTemplate;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.m295.booktopia.author.Author;
import com.m295.booktopia.author.AuthorRepository;
import com.m295.booktopia.award.Award;
import com.m295.booktopia.award.AwardRepository;
import com.m295.booktopia.book.Book;
import com.m295.booktopia.book.BookRepository;
import com.m295.booktopia.genre.Genre;
import com.m295.booktopia.genre.GenreRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@AutoConfigureDataJpa
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookControllerTests {
	
	@Autowired
    private MockMvc api;

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private AuthorRepository authorRepo;
    
    @Autowired
    private GenreRepository genreRepo;
    
    @Autowired
    private AwardRepository awardRepo;
    
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Order(value = 1)
    void testSaveVehicle() throws Exception {

    	Author author = new Author();
    	author.setFirstname("First");
    	author.setLastname("Last");
    	author.setBirthdate(LocalDate.parse("1960-07-11"));
    	authorRepo.save(author);
    	//book.setAuthor(author);


    	List<Genre> genres = new ArrayList<>();
    	Genre fictionGenre = new Genre();
    	fictionGenre.setName("fiction");
    	genres.add(fictionGenre);
    	genreRepo.saveAll(genres);
    	//book.setGenres(genres);


    	Award award = new Award();
    	award.setName("Awr");
    	award.setYear(1998);
    	awardRepo.save(award);
    	//book.setAward(award);
    	
    	Book book = new Book();
    	book.setName("To Kill a Mockingbird");
    	book.setSeries("");
    	book.setPage(281);
    	book.setReleaseDate(LocalDate.parse("1960-07-11"));
    	book.setDescription("A classic novel by Harper Lee");
    	book.setAuthor(author);
    	book.setGenres(genres);
    	book.setAward(award);


    	bookRepo.save(book);

        String accessToken = obtainAccessToken();
        String body = mapper.writeValueAsString(book);

        api.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)
                        .header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(content().string(containsString("A classic novel by Harper Lee")));
    }
    
    @Test
    @Order(value = 2)
    void testGetBooks() throws Exception {

        String accessToken = obtainAccessToken();

        api.perform(get("/api/book")
        		.header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("A classic novel by Harper Lee")));
    }
    
    @Test
    @Order(value = 3)
    void testUpdateBook() throws Exception {

    	Author author = new Author();
    	author.setFirstname("First");
    	author.setLastname("Last");
    	author.setBirthdate(LocalDate.parse("1960-07-11"));
    	authorRepo.save(author);

    	List<Genre> genres = new ArrayList<>();
    	Genre fictionGenre = new Genre();
    	fictionGenre.setName("fiction");
    	genres.add(fictionGenre);
    	genreRepo.saveAll(genres);

    	Award award = new Award();
    	award.setName("Awr");
    	award.setYear(1998);
    	awardRepo.save(award);

    	// Modify some details in "Series"
        Book book = new Book();
        book.setName("Updated Book");
    	book.setSeries("N/A");
    	book.setPage(281);
    	book.setReleaseDate(LocalDate.parse("1960-07-11"));
    	book.setDescription("A classic novel by Harper Lee");
    	book.setAuthor(author);
    	book.setGenres(genres);
    	book.setAward(award);
    	
        Book savedBook = bookRepo.save(book);

        // Perform the update request
        String accessToken = obtainAccessToken();
        String body = mapper.writeValueAsString(savedBook);

        api.perform(put("/api/book/{id}", savedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .header("Authorization", "Bearer " + accessToken)
                .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Updated Book")));
    }



	@Test
    @Order(value = 4)
    void testGetAllBooks() throws Exception {

        String accessToken = obtainAccessToken();

        api.perform(get("/api/book")
                .header("Authorization", "Bearer " + accessToken)
                .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("A classic novel by Harper Lee")));
    }

    
    @Test
    @Order(value = 5)
    void testDeleteBook() throws Exception {

    	Book book = new Book();
     	book.setId(39L);
     	book.setName("To Kill a Mockingbird");
     	book.setSeries("N/A");
     	book.setPage(281);
     	book.setReleaseDate(LocalDate.parse("1960-07-11"));
     	book.setDescription("A classic novel by Harper Lee");
        Book savedBook = bookRepo.save(book);
         
        String accessToken = obtainAccessToken();

        api.perform(delete("/api/book/{id}", savedBook.getId())
        		.header("Authorization", "Bearer " + accessToken)
                .with(csrf()))
                .andDo(print()).andExpect(status().isOk());

        // Verify deletion
        assertFalse(bookRepo.findById(book.getId()).isPresent());
    }

   
	
	 private String obtainAccessToken() {

	        RestTemplate rest = new RestTemplate();

	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

	        String body = "client_id=booktopia&" +
	                "grant_type=password&" +
	                "scope=openid profile roles offline_access&" +
	                "username=user&" +
	                "password=1234";

	        HttpEntity<String> entity = new HttpEntity<>(body, headers);

	        ResponseEntity<String> resp = rest.postForEntity("http://localhost:8080/realms/Booktopia/protocol/openid-connect/token", entity, String.class);

	        JacksonJsonParser jsonParser = new JacksonJsonParser();
	        return jsonParser.parseMap(resp.getBody()).get("access_token").toString();
	    }

}
