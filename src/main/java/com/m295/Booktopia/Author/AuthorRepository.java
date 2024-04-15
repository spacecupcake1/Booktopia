package com.m295.booktopia.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByOrderByFirstnameAscFirstnameAsc();

	//Author save(Author author);
}
