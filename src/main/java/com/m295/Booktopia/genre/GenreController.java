package com.m295.booktopia.genre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.m295.booktopia.security.Roles;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({Roles.Admin, Roles.Read, Roles.Update})
    public ResponseEntity<List<Genre>> getAllGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }
    
    @GetMapping("api/genre/{id}")
    @RolesAllowed({Roles.Admin, Roles.Read, Roles.Update})
    public ResponseEntity<Genre> getAuthorById(@PathVariable Long id) {
        return genreService.getGenreById(id)
                .map(genre -> ResponseEntity.ok().body(genre))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/api/genre")
    @RolesAllowed({Roles.Admin})
    public ResponseEntity<Genre> createGenre(@Valid @RequestBody Genre genre) {
        Genre createdGenre = genreService.createGenre(genre);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGenre);
    }
    
    @PutMapping("/api/genre/{id}")
    @RolesAllowed({Roles.Admin, Roles.Update})
    public ResponseEntity<Genre> updateGenre(@PathVariable Long id, @RequestBody Genre genre) {
    	genre.setId(id);
        return new ResponseEntity<>(genreService.updateGenre(genre), HttpStatus.OK);
    }

    @DeleteMapping("/api/genre/{id}")
    @RolesAllowed({Roles.Admin})
    public ResponseEntity<Void> deleteGenre(@PathVariable Long id) {
    	genreService.deleteGenre(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
}
