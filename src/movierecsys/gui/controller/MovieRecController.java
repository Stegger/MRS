/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import movierecsys.be.Movie;
import movierecsys.be.User;
import movierecsys.bll.exception.MrsBllException;
import movierecsys.gui.model.MovieModel;
import movierecsys.gui.model.RatingModel;
import movierecsys.gui.model.UserModel;

/**
 *
 * @author pgn
 */
public class MovieRecController implements Initializable
{

    private MovieModel movieModel;
    private UserModel userModel;
    private RatingModel ratingModel;

    @FXML
    private ListView<Movie> lstMovies;
    @FXML
    private TextField txtMovieTitle;
    @FXML
    private TextField txtMovieYear;
    @FXML
    private TextField txtMovieSearch;
    @FXML
    private TextField txtSelectedMovieTitle;
    @FXML
    private TextField txtSelectedMovieYear;
    @FXML
    private TextField txtUserSearch;
    @FXML
    private ListView<User> lstUsers;
    @FXML
    private RadioButton radioRatingMinus5;
    @FXML
    private RadioButton radioRatingMinus3;
    @FXML
    private RadioButton radioRating1;
    @FXML
    private RadioButton radioRating3;
    @FXML
    private RadioButton radioRating5;
    @FXML
    private ListView<Movie> lstRecommendedMovies;

    public MovieRecController()
    {
        try
        {
            movieModel = new MovieModel();
            userModel = new UserModel();
        } catch (MrsBllException ex)
        {
            displayError(ex);
            System.exit(0);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        lstMovies.setItems(movieModel.getMovies());
        lstUsers.setItems(userModel.getAllUsers());

        setMovieSelection();
        setUserSelection();

    }

    private void setUserSelection()
    {
        lstUsers.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lstUsers.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>()
        {
            @Override
            public void changed(ObservableValue<? extends User> arg0, User oldUser, User newUser)
            {
                userModel.setSelectedUser(newUser);
            }
        });
    }

    /**
     * Sets up the movie selection of the list of all movers.
     */
    private void setMovieSelection()
    {
        //I do this to receive updates when a new movie is selected from the list of all movies:
        lstMovies.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        lstMovies.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Movie>()
        {
            @Override
            public void changed(ObservableValue<? extends Movie> arg0, Movie oldValue, Movie newValue)
            {
                if (newValue != null)
                {
                    txtSelectedMovieTitle.setText(newValue.getTitle());
                    txtSelectedMovieYear.setText(newValue.getYear() + "");
                }
            }
        });
    }

    /**
     * Displays errormessages to the user.
     *
     * @param ex The Exception
     */
    private void displayError(Exception ex)
    {
        //TODO Display error properly
        System.out.println(ex.getMessage());
        ex.printStackTrace();
    }

    /**
     * Event handler for the "Add movie" button.
     *
     * @param event
     */
    @FXML
    private void handleAddMovie(ActionEvent event)
    {
        String title = txtMovieTitle.getText().trim();
        int year = Integer.parseInt(txtMovieYear.getText().trim());
        movieModel.createMovie(year, title);
    }

    /**
     * Event handler for the search text field. Will be invoked when a key has
     * been pressed in the field.
     *
     * @param event
     */
    @FXML
    private void handleSearchMovie(KeyEvent event)
    {
        try
        {
            String query = txtMovieSearch.getText().trim();
            movieModel.search(query);
        } catch (MrsBllException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void handleUpdateMovie(ActionEvent event)
    {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null)
        {
            try
            {
                String title = txtSelectedMovieTitle.getText();
                int year = Integer.parseInt(txtSelectedMovieYear.getText());
                selectedMovie.setTitle(title);
                selectedMovie.setYear(year);
                movieModel.updateMovie(selectedMovie);
            } catch (MrsBllException ex)
            {
                displayError(ex);
            }
        }
    }

    /**
     * Event handler for the delete movie button.
     *
     * @param event
     */
    @FXML
    private void handleDeleteMovie(ActionEvent event) throws MrsBllException
    {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        movieModel.deleteMovie(selectedMovie);
    }

    @FXML
    private void handleSearchUser(KeyEvent event)
    {
        try
        {
            String query = txtUserSearch.getText();
            userModel.searchUser(query);
        } catch (MrsBllException ex)
        {
            displayError(ex);
        }
    }

    @FXML
    private void handleUserRateMovie(ActionEvent event)
    {
        int score;

    }

}
