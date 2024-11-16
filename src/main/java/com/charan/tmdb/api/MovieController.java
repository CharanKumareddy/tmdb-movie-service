package com.charan.tmdb.api;


import com.charan.tmdb.MovieServiceApplication;
import com.charan.tmdb.model.Movie;
import com.charan.tmdb.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        Movie movie = movieService.read(id);
        log.info("Returned movie with id: {} " , id);
        return ResponseEntity.ok(movie);
    }
//    @GetMapping
//    public ResponseEntity<Movie> getMovie(Movie movie){
//        Movie getMovie = movieService.get(movie);
//        log.info("Returned movie with id: {} " , movie);
//        return ResponseEntity.ok(getMovie);
//    }

    @PostMapping
    public ResponseEntity<Movie> sendMovie(@RequestBody Movie movie){
        Movie createdMovie = movieService.create(movie);
        log.info("Created movie with id: {} " , movie.getId() + " " + movie.getName() + " " + movie.getDirector() + " " + movie.getActors());
        return ResponseEntity.ok(createdMovie);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id, @RequestBody Movie movie){
        log.info("Updated movie with id: {} " , movie.getId());
        movieService.update(id, movie);
    }

    @DeleteMapping("/{id}")
    public void deleteMovie(@PathVariable Long id){
        log.info("Deleted movie with id: {} " , id);
        movieService.delete(id);
    }
}
