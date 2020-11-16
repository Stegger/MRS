/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.bll.util;

import java.util.*;

import movierecsys.be.Movie;
import movierecsys.be.Rating;
import org.jetbrains.annotations.NotNull;

/**
 * @author pgn
 */
public class MovieRecommender {


    /**
     * Returns a list of movie recommendations based on the highest total recommendations. Excluding already rated movies from the list of results.
     *
     * @param allRatings     List of all users ratings.
     * @param excludeRatings List of Ratings (aka. movies) to exclude.
     * @return Sorted list of movies recommended to the caller. Sorted in descending order.
     */
    public List<Movie> highAverageRecommendations(List<Movie> allMovies, List<Rating> allRatings, List<Rating> excludeRatings) {
        List<Rating> tmpRatings = removeExcludedRatings(allRatings, excludeRatings);
        HashMap<Integer, Integer> movieScoreSums = getMovieTotalSums(tmpRatings);
        HashMap<Integer, Integer> movieRatingsCount = getMovieTotalRatingCount(tmpRatings);
        Movie[] movies = createSortedMovieArray(allMovies);
        HashMap<Movie, Double> movieScores = getMovieAverageScore(movieScoreSums, movieRatingsCount, movies);
        List<Movie> reccomendations = getSortedMovieRecommendations(allMovies, movieScores);
        return reccomendations;
    }

    /**
     * Gets a mapping of "MovieID : ratingCount" from the given list of ratings.
     *
     * @param tmpRatings The list of ratings.
     * @return The mapping of how many ratings there is for a given movie.
     */
    private HashMap<Integer, Integer> getMovieTotalRatingCount(List<Rating> tmpRatings) {
        tmpRatings.sort(Comparator.comparingInt(Rating::getMovie));
        HashMap<Integer, Integer> countOfMovies = new HashMap<>();
        for (Rating r : tmpRatings) {
            int count;
            if (!countOfMovies.containsKey(r.getMovie())) {
                count = 1;
            } else {
                count = countOfMovies.get(r.getMovie()) + 1;
            }
            countOfMovies.put(r.getMovie(), count);
        }
        return countOfMovies;
    }

    /**
     * Given the two list of ratings remove all ratings from "allRatings" that rates a movie found in the "excludeRatings" list.
     *
     * @param allRatings     The list to remove ratings fromm.
     * @param excludeRatings The list with ratings that marks movies to exclude.
     * @return The reduces allRatings list where matches found in excludeRatings is removed from.
     */
    @NotNull
    private List<Rating> removeExcludedRatings(List<Rating> allRatings, List<Rating> excludeRatings) {
        List<Rating> tmpRatings = new ArrayList<>(allRatings);
        tmpRatings.removeIf(rating -> excludeRatings.stream().anyMatch(r -> rating.getMovie() == r.getMovie()));
        return tmpRatings;
    }

    /**
     * Given the list of all movies the method returns a sorted list of movies. The sorting is based on the data found in the given movie scores parameter.
     *
     * @param allMovies   The list of movies to sort.
     * @param movieScores The hasmap of movies and scores.
     * @return The sorted list of Movies.
     */
    @NotNull
    private List<Movie> getSortedMovieRecommendations(List<Movie> allMovies, HashMap<Movie, Double> movieScores) {
        List<Movie> recommendations = new ArrayList<>(allMovies);
        recommendations.removeIf(movie -> !movieScores.containsKey(movie));

        recommendations.sort((o1, o2) -> {
            double diff;
            if (movieScores.containsKey(o1) && movieScores.containsKey(02)) {
                diff = movieScores.get(o1) - movieScores.get(o2);
            } else if (movieScores.containsKey(o1)) {
                diff = -movieScores.get(o1);
            } else
                diff = movieScores.get(o2);

            if (diff < 0.0)
                return -1;
            else if (diff > 0.0)
                return 1;
            else
                return 0;
        });
        return recommendations;
    }

    /**
     * Gets the sum of all ratings for all movies in a list of movie ratings.
     *
     * @param tmpRatings The list of ratings of movies.
     * @return A mapping of movieID and the sum of all ratings for the movie with said ID.
     */
    @NotNull
    private HashMap<Integer, Integer> getMovieTotalSums(List<Rating> tmpRatings) {
        tmpRatings.sort(Comparator.comparingInt(Rating::getMovie));
        HashMap<Integer, Integer> sumOfRatings = new HashMap<>();
        for (Rating r : tmpRatings) {
            int sum;
            if (!sumOfRatings.containsKey(r.getMovie())) {
                sum = r.getRating();
            } else {
                sum = r.getRating() + sumOfRatings.get(r.getMovie());
            }
            sumOfRatings.put(r.getMovie(), sum);
        }
        return sumOfRatings;
    }

    /**
     * Calculates the average score for all movies based on the given movie rating sums and movie rating counts.
     *
     * @param movieRatingsScoreSum The sum of the movies rating score
     * @param movieRatingsCount    The count of the movies ratings. (How many ratings for each movie).
     * @param ratedMovies          The array of all movies in the ratings.
     * @return A map of the ratings for the individual movies.
     */
    @NotNull
    private HashMap<Movie, Double> getMovieAverageScore(HashMap<Integer, Integer> movieRatingsScoreSum, HashMap<Integer, Integer> movieRatingsCount, Movie[] ratedMovies) {
        HashMap<Movie, Double> movieScores = new HashMap<>();
        for (int movieId : movieRatingsScoreSum.keySet()) {
            int i = Arrays.binarySearch(ratedMovies, new Movie(movieId, 0, ""), Comparator.comparingInt(Movie::getId));
            if (i > -1) {
                double avg = ((double) movieRatingsScoreSum.get(movieId)) / ((double) movieRatingsCount.get(movieId));
                Movie m = ratedMovies[i];
                movieScores.put(m, avg);
            }
        }
        return movieScores;
    }

    /**
     * Creates a simple sorted array of movie objects from the given list of movies.
     * @param allMovies The base list of movies.
     * @return A sorted array of movies.
     */
    @NotNull
    private Movie[] createSortedMovieArray(List<Movie> allMovies) {
        allMovies.sort(Comparator.comparing(Movie::getId));
        Movie[] movies = new Movie[allMovies.size()];
        allMovies.toArray(movies);
        return movies;
    }

    /**
     * Returns a list of movie recommendations based on weighted recommendations. Excluding already rated movies from the list of results.
     *
     * @param allRatings     List of all users ratings.
     * @param excludeRatings List of Ratings (aka. movies) to exclude.
     * @return Sorted list of movies recommended to the caller. Sorted in descending order.
     */
    public List<Movie> weightedRecommendations(List<Movie> allMovies, List<Rating> allRatings, List<Rating> excludeRatings) {
        //TODO Weighted recommender
        return null;
    }

}
