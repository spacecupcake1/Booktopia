package com.m295.Booktopia;

import java.time.LocalDate;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.m295.booktopia.author.Author;
import com.m295.booktopia.author.AuthorRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class DataBaseTests {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    void insertAuthor() {
    	 Author objAuthor = this.authorRepository.save(new Author());
         Assertions.assertNotNull(objAuthor.getId());
     }
}

