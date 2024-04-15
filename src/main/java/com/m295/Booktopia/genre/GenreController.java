package com.m295.booktopia.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.m295.booktopia.author.Author;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@SecurityRequirement(name = "bearerAuth")
@Validated
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/api/genre")
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @PostMapping("/api/genre")
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) {
        Genre createdGenre = genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }
    
    @GetMapping("api/genre/{id}")
    public ResponseEntity<Genre> getAuthorById(@PathVariable Long id) {
        return genreService.getGenreById(id)
                .map(genre -> ResponseEntity.ok().body(genre))
                .orElse(ResponseEntity.notFound().build());
    }
}
