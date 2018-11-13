/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movierecsys.be.Movie;
import movierecsys.bll.MRSLogicFacade;
import movierecsys.bll.MRSManager;
import movierecsys.bll.exception.MovieRecSysException;

/**
 *
 * @author pgn
 */
public class MovieModel
{

    private ObservableList<Movie> movies;

    private MRSLogicFacade logiclayer;

    public MovieModel() throws MovieRecSysException
    {
        movies = FXCollections.observableArrayList();
        logiclayer = new MRSManager();
        movies.addAll(logiclayer.getAllMovies());
    }

    /**
     * Gets a reference to the observable list of Movies.
     * @return List of movies.
     */
    public ObservableList<Movie> getMovies()
    {
        return movies;
    }

    public void createMovie(int year, String title)
    {
        Movie movie = logiclayer.createMovie(year, title);
        movies.add(movie);
    }
    
    public void deleteMovie(Movie movie)
    {
        logiclayer.deleteMovie(movie);
        movies.remove(movie);
    }
    
}
