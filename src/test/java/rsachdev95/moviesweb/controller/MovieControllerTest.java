package rsachdev95.moviesweb.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import rsachdev95.moviesweb.model.Movie;
import rsachdev95.moviesweb.service.MovieService;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ExtendWith(MockitoExtension.class)
public class MovieControllerTest {

    private static final String MOVIES_PATH = "/movies";
    private static final String ID = "id";
    private static final String MOVIE_PATH = MOVIES_PATH + "/" + ID;

    private static final String TEMPLATE_INDEX = "index";
    private static final String TEMPLATE_ERROR = "error";
    private static final String TEMPLATE_MOVIE = "movie";

    private MockMvc mockMvc;

    @Mock
    List<Movie> movies;

    @Mock
    Movie movie;

    @Mock
    private MovieService movieService;

    @InjectMocks
    private MovieController movieController;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(movieController).build();
    }

    @Test
    @DisplayName("Tests successful list of all movies")
    public void testListRequestSuccessful() throws Exception {
        when(movieService.listAll()).thenReturn(movies);

        this.mockMvc.perform(get(MOVIES_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_INDEX))
                .andExpect(model().attributeExists("movies"));
    }

    @Test
    @DisplayName("Tests unsuccessful list of all movies")
    public void testListRequestUnsuccessful() throws Exception {
        when(movieService.listAll()).thenReturn(Collections.emptyList());

        this.mockMvc.perform(get(MOVIES_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_ERROR));
    }

    @Test
    @DisplayName("Tests successful retrieval of top commenter")
    public void testGetTopCommenterSuccessful() throws Exception {
        when(movieService.findTopCommenter()).thenReturn("commenter");

        this.mockMvc.perform(get(MOVIES_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_INDEX))
                .andExpect(model().attributeExists("topCommenter"));
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of top commenter")
    public void testGetTopCommenterUnsuccessful() throws Exception {
        when(movieService.findTopCommenter()).thenReturn(null);

        this.mockMvc.perform(get(MOVIES_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_ERROR));
    }

    @Test
    @DisplayName("Tests successful retrieval of movie with most likes")
    public void testGetMostLikedMovieSuccessful() throws Exception {
        when(movieService.findMovieWithMostLikes()).thenReturn("movie");

        this.mockMvc.perform(get(MOVIES_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_INDEX))
                .andExpect(model().attributeExists("movieWithMostLikes"));
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of movie with most likes")
    public void testGetMostLikedMovieUnsuccessful() throws Exception {
        when(movieService.findMovieWithMostLikes()).thenReturn(null);

        this.mockMvc.perform(get(MOVIES_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_ERROR));
    }

    @Test
    @DisplayName("Tests successful retrieval of movie by id")
    public void testGetMovieByIdSuccessful() throws Exception {
        when(movieService.findById(ID)).thenReturn(movie);

        this.mockMvc.perform(get(MOVIE_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_MOVIE))
                .andExpect(model().attributeExists("movie"));
    }

    @Test
    @DisplayName("Tests unsuccessful retrieval of movie by id")
    public void testGetMovieByIdUnsuccessful() throws Exception {
        when(movieService.findById(ID)).thenReturn(null);

        this.mockMvc.perform(get(MOVIE_PATH))
                .andExpect(status().isOk())
                .andExpect(view().name(TEMPLATE_ERROR));
    }
}
