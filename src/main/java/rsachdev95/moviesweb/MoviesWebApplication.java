package rsachdev95.moviesweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MoviesWebApplication {

	public static final String APPLICATION_NAME_SPACE = "movies-web";

	public static void main(String[] args) {
		SpringApplication.run(MoviesWebApplication.class, args);
	}

}
