package rsachdev95.moviesweb.service;

import rsachdev95.moviesweb.model.Movie;

import java.util.List;

public interface MovieService {
    List<Movie> listAll();
    Movie findById(String id);
    String findTopCommenter();
    String findMovieWithMostLikes();
}
