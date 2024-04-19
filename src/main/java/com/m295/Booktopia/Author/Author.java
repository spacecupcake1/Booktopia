package com.m295.booktopia.author;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.m295.booktopia.book.Book;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@Entity
public class Author {
    
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String firstname;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String lastname;
    
    @Column(nullable = true)
    @NotEmpty
    private LocalDate birthdate;
    
    public Author(Long id, String firstname, String lastname, LocalDate birthdate) {
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthdate = birthdate;
	}

	public Author() {
		
	}


}
