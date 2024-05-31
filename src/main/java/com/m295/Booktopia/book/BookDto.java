package com.m295.booktopia.book;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookDto {
	
	    private Long id;

	    private String name;
	    
	    private String series;
	    
	    private int page;
	    
	    private LocalDate releaseDate;
	    
	    private String description;
	    
	    private Long authorId;

	    private Long genreId;
	    
	    private Long awardId;


}
