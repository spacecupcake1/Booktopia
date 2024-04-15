package com.m295.booktopia.author;

import java.sql.Date;

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
    
    @Column(length = 10, nullable = true)
    @NotEmpty
    private Date birthdate;
    
}
