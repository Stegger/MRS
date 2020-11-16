/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.bll;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import movierecsys.be.Movie;
import movierecsys.be.Rating;
import movierecsys.be.User;
import movierecsys.bll.exception.MrsBllException;
import movierecsys.bll.util.MovieSearcher;
import movierecsys.dal.DalController;
import movierecsys.dal.MrsDalInterface;
import movierecsys.dal.exception.MrsDalException;

/**
 *
 * @author pgn
 */
public class MRSManager implements MRSLogicFacade
{

    private final MrsDalInterface dalFacade;

    

    public MRSManager() throws MrsBllException
    {
        try
        {
            dalFacade = new DalController();
        } catch (IOException ex)
        {
            throw new MrsBllException("Could not connect to DAL layer.");
        }
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
    public List<Movie> searchMovies(String query) throws MrsBllException
    {
        List<Movie> allMovies = getAllMovies();
        allMovies = MovieSearcher.search(allMovies, query);
        return allMovies;
    }

    @Override
    public Movie createMovie(int year, String title) throws MrsBllException
    {
        try
        {
            
            return dalFacade.createMovie(year, title);
        } catch (MrsDalException ex)
        {
            Logger.getLogger(MRSManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new MrsBllException("Could not create movie.");
        }
    }

    /**
     * Updates a movie.
     *
     * @param movie
     * @throws MrsBllException
     */
    @Override
    public void updateMovie(Movie movie) throws MrsBllException
    {
        try
        {
            dalFacade.updateMovie(movie);
        } catch (MrsDalException ex)
        {
            Logger.getLogger(MRSManager.class.getName()).log(Level.SEVERE, null, ex);
            throw new MrsBllException("Could not update movie");
        }
    }

    /**
     *
     * @param movie
     * @throws MrsBllException
     */
    @Override
    public void deleteMovie(Movie movie) throws MrsBllException
    {
        if (movie != null)
        {
            try
            {
                dalFacade.deleteMovie(movie);
            } catch (MrsDalException ex)
            {
                Logger.getLogger(MRSManager.class.getName()).log(Level.SEVERE, null, ex);
                throw new MrsBllException("Could not delete Message");
            }
        }
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
    public List<User> getAllUsers() throws MrsBllException
    {
        try
        {
            List<User> allUsers = dalFacade.getAllUsers();
            return allUsers;
        } catch (MrsDalException ex)
        {
            ex.printStackTrace();
            throw new MrsBllException("Could not get all users.");
        }
    }

    /**
     * Gets all movies.
     *
     * @return List of movies.
     * @throws MrsBllException
     */
    @Override
    public List<Movie> getAllMovies() throws MrsBllException
    {
        try
        {
            return dalFacade.getAllMovies();
        } catch (MrsDalException ex)
        {
            throw new MrsBllException("Could not read all movies. Cause: " + ex.getMessage());
        }
    }

    @Override
    public List<User> searchUsers(String query) throws MrsBllException
    {
        try
        {
            List<User> allUsers = dalFacade.getAllUsers();
            allUsers.removeIf((User user) -> !user.getName().toLowerCase().contains(query.toLowerCase()));
            return allUsers;
        } catch (MrsDalException ex)
        {
            ex.printStackTrace();
            throw new MrsBllException("Unable to perform search for user.");
        }
    }

}
