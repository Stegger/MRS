/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package movierecsys.bll;

import java.io.IOException;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;
import movierecsys.bll.exception.MovieRecSysException;
import movierecsys.dal.MovieDAO;

/**
 *
 * @author pgn
 */
public class MRSManager implements MRSLogicFacade {

    private final MovieDAO movieDAO;
    
    public MRSManager()
    {
        movieDAO = new MovieDAO();
    }
    
    @Override
    public List<Rating> getRecommendedMovies(User user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movie> getAllTimeTopRatedMovies()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movie> getMovieReccomendations(User user)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movie> searchMovies(String query)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Movie createMovie(int year, String title)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateMovie(Movie movie)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteMovie(Movie movie)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void rateMovie(Movie movie, User user, int rating)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User createNewUser(String name)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User getUserById(int id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> getAllUsers()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Gets all movies.
     * @return List of movies.
     * @throws MovieRecSysException
     */
    @Override
    public List<Movie> getAllMovies() throws MovieRecSysException
    {
        try
        {
            return movieDAO.getAllMovies();
        } catch (IOException ex)
        {
//            Logger.getLogger(MRSManager.class.getName()).log(Level.SEVERE, null, ex); You could log an exception
            throw new MovieRecSysException("Could not read all movies. Cause: " + ex.getMessage());
        }
    }

}
