/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import movierecsys.be.Movie;
import movierecsys.bll.exception.MovieRecSysException;
import movierecsys.gui.model.MovieModel;

/**
 *
 * @author pgn
 */
public class MovieRecController implements Initializable
{

    /**
     * The TextField containing the URL of the targeted website.
     */
    @FXML
    private TextField txtMovieSearcjh;

    /**
     * The TextField containing the query word.
     */
    @FXML
    private ListView<Movie> lstMovies;

    private MovieModel movieModel;

    public MovieRecController()
    {
        try
        {
            movieModel = new MovieModel();
        } catch (MovieRecSysException ex)
        {
            displayError(ex);
            System.exit(0);
        } 
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        lstMovies.setItems(movieModel.getMovies());
    }

    /**
     * Displays errormessages to the user.
     * @param ex The Exception
     */
    private void displayError(Exception ex)
    {
        //TODO Display error properly
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }

}
