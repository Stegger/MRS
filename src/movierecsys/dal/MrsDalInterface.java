/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.IOException;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;
import movierecsys.dal.exception.MrsDalException;

/**
 *
 * @author pgn
 */
public interface MrsDalInterface
{

    Movie createMovie(int releaseYear, String title) throws MrsDalException;

    void createRating(Rating rating) throws MrsDalException;

    void deleteMovie(Movie movie) throws MrsDalException;

    void deleteRating(Rating rating) throws MrsDalException;

    List<Movie> getAllMovies() throws MrsDalException;

    List<Rating> getAllRatings() throws MrsDalException;

    List<User> getAllUsers() throws MrsDalException;

    Movie getMovie(int id) throws MrsDalException;

    List<Rating> getRatings(User user) throws MrsDalException;

    User getUser(int id) throws MrsDalException;

    void updateMovie(Movie movie) throws MrsDalException;

    void updateRating(Rating rating) throws MrsDalException;

    void updateUser(User user) throws MrsDalException;

}
