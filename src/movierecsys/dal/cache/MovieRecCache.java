package movierecsys.dal.cache;

import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * A cache to insert into the DAL layer (Once it's fully implemented). Use it to speed up performance.
 */
public class MovieRecCache implements Serializable {

    private List<Movie> movies;
    private List<User> users;
    private List<Rating> ratings;

    public MovieRecCache() {
        movies = new ArrayList<>();
        users = new ArrayList<>();
        ratings = new ArrayList<>();
    }

    public void saveMovies(Movie... movies) {
        for (Movie movie : movies) {
            this.movies.removeIf(m -> m.getId() == movie.getId());
            this.movies.add(movie);
        }
    }

    public void setAllMovies(List<Movie> allMovies) {
        this.movies = allMovies;
    }

    public Movie getMovie(int id) {
        int i = Arrays.binarySearch(movies.toArray(), (Predicate<Movie>) movie -> movie.getId() == id);
        if (i >= 0)
            return movies.get(i);
        else
            return null;
    }

    public void removeMovie(Movie movie) {
        movies.removeIf(m -> m.getId() == movie.getId());
    }


    public boolean isUpdated() {
        return false;
    }

    public List<Movie> getAllMovies() {
        return null;
    }
}