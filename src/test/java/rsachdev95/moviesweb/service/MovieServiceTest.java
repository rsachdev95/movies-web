package rsachdev95.moviesweb.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import rsachdev95.moviesweb.model.Movie;
import rsachdev95.moviesweb.service.impl.MovieServiceImpl;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation.Builder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {
    private static final String BASE_URL = "http://localhost:8080/movies";
    private static final String ID = "1";
    private static final String TOP_COMMENTER = "topCommenter";
    private static final String MOVIE_WITH_MOST_LIKES = "movie";

    @Mock
    private Response response;

    @Mock
    private Client client;

    @Mock
    WebTarget webTarget;

    @Mock
    Builder builder;

    @Mock
    List<Movie> movies;

    @Mock
    Movie movie;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        when(webTarget.request(MediaType.APPLICATION_JSON)).thenReturn(builder);
        when(builder.get()).thenReturn(response);
    }

    @Test
    @DisplayName("Tests successful get of all movies")
    public void testGetAllMoviesSuccessful() {
        when(client.target(BASE_URL)).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.OK.value());
        when(response.readEntity(new GenericType<List<Movie>>() {})).thenReturn(movies);

        List<Movie> moviesFromService = movieService.listAll();
        assertNotNull(moviesFromService);
        assertEquals(movies, moviesFromService);
    }

    @Test
    @DisplayName("Tests unsuccessful get of all movies")
    public void testGetAllMoviesUnsuccessful() {
        when(client.target(BASE_URL)).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_FOUND.value());

        List<Movie> movies = movieService.listAll();
        assertEquals(Collections.emptyList(), movies);
    }

    @Test
    @DisplayName("Tests successful find movie by id")
    public void testFindMovieByIdSuccessful() {
        when(client.target(BASE_URL + "/" + ID)).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.OK.value());
        when(response.readEntity(Movie.class)).thenReturn(movie);

        Movie movieFromService = movieService.findById(ID);
        assertNotNull(movieFromService);
        assertEquals(movie, movieFromService);
    }

    @Test
    @DisplayName("Tests unsuccessful find movie by id")
    public void testFindMovieByIdUnsuccessful() {
        when(client.target(BASE_URL + "/" + ID)).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_FOUND.value());

        Movie movieFromService = movieService.findById(ID);
        assertNull(movieFromService);
    }

    @Test
    @DisplayName("Tests successful find top commenter")
    public void testFindTopCommenterSuccessful() {
        when(client.target(BASE_URL + "/most-comments")).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.OK.value());
        when(response.readEntity(String.class)).thenReturn(TOP_COMMENTER);

        String topCommenter = movieService.findTopCommenter();
        assertNotNull(topCommenter);
        assertEquals(TOP_COMMENTER, topCommenter);
    }

    @Test
    @DisplayName("Tests unsuccessful find top commenter")
    public void testFindTopCommenterUnsuccessful() {
        when(client.target(BASE_URL + "/most-comments")).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_FOUND.value());

        String topCommenter = movieService.findTopCommenter();
        assertNull(topCommenter);
    }

    @Test
    @DisplayName("Tests successful find movie with most likes")
    public void testSuccessfulFindMovieWithMostLikes() {
        when(client.target(BASE_URL + "/most-likes")).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.OK.value());
        when(response.readEntity(String.class)).thenReturn(MOVIE_WITH_MOST_LIKES);

        String movieWithMostLikes = movieService.findMovieWithMostLikes();
        assertNotNull(movieWithMostLikes);
        assertEquals(MOVIE_WITH_MOST_LIKES, movieWithMostLikes);
    }

    @Test
    @DisplayName("Tests unsuccessful find movie with most likes")
    public void testsUnsuccessfulFindMovieWithMostLikes() {
        when(client.target(BASE_URL + "/most-likes")).thenReturn(webTarget);
        when(response.getStatus()).thenReturn(HttpStatus.NOT_FOUND.value());

        String movieWithMostLikes = movieService.findMovieWithMostLikes();
        assertNull(movieWithMostLikes);
    }
}
