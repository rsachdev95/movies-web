package rsachdev95.moviesweb.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import rsachdev95.moviesweb.MoviesWebApplication;
import rsachdev95.moviesweb.model.Movie;
import rsachdev95.moviesweb.service.MovieService;

import java.util.List;

@Controller
@RequestMapping("movies")
public class MovieController {

    private static final Logger LOG = LoggerFactory.getLogger(MoviesWebApplication.APPLICATION_NAME_SPACE);

    @Autowired
    private MovieService movieService;

    @GetMapping
    public String listAllMovies(Model model) {
        LOG.info("Getting a list of all movies");
        List<Movie> movies = movieService.listAll();
        String topCommenter = movieService.findTopCommenter();
        String movieWithMostLikes = movieService.findMovieWithMostLikes();

        if(movies.isEmpty() && topCommenter == null && movieWithMostLikes == null) {
            return "error";
        }

        model.addAttribute("movies", movies);
        model.addAttribute("topCommenter", topCommenter);
        model.addAttribute("movieWithMostLikes", movieWithMostLikes);

        return "index";
    }

    @GetMapping("/{movie-id}")
    public String getMovieById(@PathVariable("movie-id") String id, Model model) {
        LOG.info("Getting movie with id: " + id);
        Movie movie = movieService.findById(id);

        if(movie == null) {
            return "error";
        }

        model.addAttribute("movie", movie);

        return "movie";
    }
}
