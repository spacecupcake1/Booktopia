package com.example.Booktopia.Author;

import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Data
@Entity
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String name;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String firstname;
    
    
}
