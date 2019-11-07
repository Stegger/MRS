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
import movierecsys.dal.file.MovieDAO;
import movierecsys.dal.intereface.IMovieRepository;
import movierecsys.dal.intereface.IRatingRepository;
import movierecsys.dal.intereface.IUserRepository;

/**
 *
 * @author pgn
 */
public class DalController implements MrsDalInterface
{

    private IMovieRepository movieRepo;
    private IUserRepository userRepo;
    private IRatingRepository ratingRepo;

    public DalController() throws IOException
    {
        movieRepo = new MovieDAO();
    }

    @Override
    public Movie createMovie(int releaseYear, String title) throws MrsDalException
    {
        return movieRepo.createMovie(releaseYear, title); //LOG the error
    }

    @Override
    public void deleteMovie(Movie movie) throws MrsDalException
    {
        movieRepo.deleteMovie(movie);
    }

    @Override
    public List<Movie> getAllMovies() throws MrsDalException
    {
        return movieRepo.getAllMovies();
    }

    @Override
    public Movie getMovie(int id) throws MrsDalException
    {
        return movieRepo.getMovie(id);
    }

    @Override
    public void updateMovie(Movie movie) throws MrsDalException
    {
        movieRepo.updateMovie(movie);
    }

    @Override
    public void createRating(Rating rating)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteRating(Rating rating)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rating> getAllRatings() throws MrsDalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Rating> getRatings(User user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateRating(Rating rating) throws MrsDalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllUsers()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUser(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateUser(User user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
