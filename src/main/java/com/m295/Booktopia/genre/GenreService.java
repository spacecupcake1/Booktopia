package com.m295.booktopia.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
	
	@Autowired
    private GenreRepository genreRepo;


    @Autowired
    public GenreService(GenreRepository genreRepo) {
        this.genreRepo = genreRepo;
    }

    public List<Genre> getAllGenres() {
        return genreRepo.findAll();
    }

    public Genre createGenre(Genre genre) {
        return genreRepo.save(genre);
    }
    
    public Optional<Genre> getGenreById(Long id) {
        return genreRepo.findById(id);
    }
}