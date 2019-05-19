package rsachdev95.moviesweb.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import rsachdev95.moviesweb.MoviesWebApplication;
import rsachdev95.moviesweb.model.Movie;
import rsachdev95.moviesweb.service.MovieService;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collections;
import java.util.List;

@Service
public class MovieServiceImpl implements MovieService {
    private static final Logger LOG = LoggerFactory.getLogger(MoviesWebApplication.APPLICATION_NAME_SPACE);

    private static final String BASE_URL = "http://localhost:8080/movies";

    private Client client = ClientBuilder.newClient();

    public List<Movie> listAll() {
        Response response = client.target(BASE_URL).request(MediaType.APPLICATION_JSON).get();

        if(response.getStatus() != HttpStatus.OK.value()) {
            LOG.error("Unable to retrieve movies");
            return Collections.emptyList();
        }

        return response.readEntity(new GenericType<List<Movie>>() {});
    }

    public Movie findById(String id) {
        Response response = client.target(BASE_URL + "/" + id)
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        if(response.getStatus() != HttpStatus.OK.value()) {
            LOG.error("Movie with id: " + id + " not found");
            return null;
        }

        return response.readEntity(Movie.class);
    }

    public String findTopCommenter() {
        Response response = client.target(BASE_URL + "/most-comments")
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        if(response.getStatus() != HttpStatus.OK.value()) {
            LOG.error("Top commenter not found");
            return null;
        }

        return response.readEntity(String.class);
    }

    public String findMovieWithMostLikes() {
        Response response = client.target(BASE_URL + "/most-likes")
                                  .request(MediaType.APPLICATION_JSON)
                                  .get();

        if(response.getStatus() != HttpStatus.OK.value()) {
            LOG.error("Movie with most likes not found");
            return null;
        }

        return response.readEntity(String.class);
    }
}
