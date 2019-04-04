/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.intereface;

import java.util.List;
import movierecsys.be.Movie;
import movierecsys.dal.exception.MrsDalException;

/**
 *
 * @author pgn
 */
public interface IMovieRepository
{

    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     * @throws movierecsys.dal.exception.MrsDalException
     */
    Movie createMovie(int releaseYear, String title) throws MrsDalException;

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     * @throws movierecsys.dal.exception.MrsDalException
     */
    void deleteMovie(Movie movie) throws MrsDalException;

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     * @throws movierecsys.dal.exception.MrsDalException
     */
    List<Movie> getAllMovies() throws MrsDalException;

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     * @throws movierecsys.dal.exception.MrsDalException
     */
    Movie getMovie(int id) throws MrsDalException;

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     * @throws movierecsys.dal.exception.MrsDalException
     */
    void updateMovie(Movie movie) throws MrsDalException;
    
}
