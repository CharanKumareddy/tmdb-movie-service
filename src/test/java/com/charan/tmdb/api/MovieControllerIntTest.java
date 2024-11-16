package com.charan.tmdb.api;

import com.charan.tmdb.model.Movie;
import com.charan.tmdb.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
public class MovieControllerIntTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    MovieRepository movieRepository;

    @BeforeEach
    void cleanUp(){
        movieRepository.deleteAllInBatch();
    }

    @Test
    public void givenMovie_whenCreatedMovie_thenReturnSavedMovie() throws Exception {

        //Given Movie
        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("SS Rajamouli");
        movie.setActors(List.of("Ram Charan", "NTR", "Alia Bhatt", "Ajay Devagan"));

        //When Created Movie
        var response = mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        //Then Return Saved Movie
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
    }


    @Test
    public void givenMovieId_whenFetchMovie_thenReturnMovie() throws Exception {

        Movie movie = new Movie();
        movie.setName("RRR");
        movie.setDirector("SS Rajamouli");
        movie.setActors(List.of("Ram Charan", "NTR", "Alia Bhatt", "Ajay Devagan"));

        Movie savedMovie = movieRepository.save(movie);

        //When Fetch Movie
        var response = mockMvc.perform(get("/movies/" + savedMovie.getId()));

        //Then Verify Save Movie
        response.andDo(print())

                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedMovie.getId())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect(jsonPath("$.actors", is(movie.getActors())));
        

    }


}

