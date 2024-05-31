package com.m295.booktopia.book;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.m295.booktopia.author.Author;
import com.m295.booktopia.award.Award;
import com.m295.booktopia.genre.Genre;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String name;
    
    @Column(length = 100, nullable = false)
    @Size(max = 100)
    @NotEmpty
    private String series;
    
    @Column(length = 10, nullable = false)
    @Size(max = 10)
    @NotEmpty
    private int page;
    
    @Column(nullable = true)
    @NotEmpty
    private LocalDate releaseDate;
    
    @Column(length = 1000, nullable = true)
    @Size(max = 1000)
    @NotEmpty
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "author_id")
    @Cascade(CascadeType.ALL)
    private Author author;
    
    @ManyToMany
    @JoinTable(
        name = "book_genre",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "genre_id"))
    @Cascade(CascadeType.ALL)
    private List<Genre> genres;
    
    @ManyToOne
    @JoinColumn(name = "award_id")
    @Cascade(CascadeType.ALL)
    private Award award;

}
