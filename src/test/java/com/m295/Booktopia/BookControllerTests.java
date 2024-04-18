package com.m295.Booktopia;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void testSaveVehicle() throws Exception {

    	Author author = new Author();
    	author.setFirstname("First");
    	author.setLastname("Last");
    	author.setBirthdate(LocalDate.parse("1960-07-11"));
    	authorRepo.save(author);
    	//book.setAuthor(author);


    	List<Genre> genres = new ArrayList<>();
    	Genre fictionGenre = new Genre();
    	genres.add(fictionGenre);
    	Genre dramaGenre = new Genre();
    	genres.add(dramaGenre);
    	//book.setGenres(genres);


    	Award award = new Award();
    	award.setName("Awr");
    	award.setYear(1998);
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
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Description")));
    }
    
    @Test
    void testGetBooks() throws Exception {

        String accessToken = obtainAccessToken();

        api.perform(get("/api/book")
        		.header("Authorization", "Bearer " + accessToken)
                        .with(csrf()))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("Test Book")));
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
