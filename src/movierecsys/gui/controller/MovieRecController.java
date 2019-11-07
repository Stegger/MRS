/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import movierecsys.be.Movie;
import movierecsys.bll.exception.MrsBllException;
import movierecsys.gui.model.MovieModel;

/**
 *
 * @author pgn
 */
public class MovieRecController implements Initializable
{

    /**
     * The TextField containing the query word.
     */
    @FXML
    private ListView<Movie> lstMovies;
    @FXML
    private TextField txtMovieTitle;
    @FXML
    private TextField txtMovieYear;
    @FXML
    private TextField txtMovieSearch;
    
    private MovieModel movieModel;
    
    public MovieRecController()
    {
        try
        {
            movieModel = new MovieModel();
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
            Logger.getLogger(MovieRecController.class.getName()).log(Level.SEVERE, null, ex);
            displayError(ex);
        }
    }
    
    @FXML
    private void handleUpdateMovie(ActionEvent event)
    {
        Movie selectedMovie = lstMovies.getSelectionModel().getSelectedItem();
        if (selectedMovie != null)
        {
            // Create the custom dialog.
            Dialog<Pair<String, Integer>> dialog = new Dialog<>();
            dialog.setTitle("Update movie");
            dialog.setHeaderText("Update: " + selectedMovie);

            // Set the button types.
            ButtonType updateButton = new ButtonType("Update", ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(updateButton, ButtonType.CANCEL);

            // Create the txtTitle and txtYear labels and fields.
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));
            
            TextField txtTitle = new TextField(selectedMovie.getTitle());
            txtTitle.setPromptText("Title");
            TextField txtYear = new TextField(Integer.toString(selectedMovie.getYear()));
            txtYear.setPromptText("Year");
            
            grid.add(new Label("Title:"), 0, 0);
            grid.add(txtTitle, 1, 0);
            grid.add(new Label("Year:"), 0, 1);
            grid.add(txtYear, 1, 1);

            // Enable/Disable update button depending on whether a txtTitle was entered.
            Node btnUpdate = dialog.getDialogPane().lookupButton(updateButton);
            btnUpdate.setDisable(true);

            // Do some validation (using the Java 8 lambda syntax).
            txtTitle.textProperty().addListener((observable, oldValue, newValue) ->
            {
                btnUpdate.setDisable(newValue.trim().isEmpty());
            });
            
            dialog.getDialogPane().setContent(grid);

            // Request focus on the txtTitle field by default.
            Platform.runLater(() -> txtTitle.requestFocus());

            // Convert the result to a txtTitle-txtYear-pair when the login button is clicked.
            dialog.setResultConverter(dialogButton ->
            {
                if (dialogButton == updateButton)
                {
                    return new Pair<>(txtTitle.getText(), Integer.parseInt(txtYear.getText()));
                }
                return null;
            });
            
            Optional<Pair<String, Integer>> result = dialog.showAndWait();
            
            result.ifPresent(titleYear ->
            {
                try
                {
                    String title = titleYear.getKey();
                    int year = titleYear.getValue();
                    selectedMovie.setTitle(title);
                    selectedMovie.setYear(year);
                    movieModel.updateMovie(selectedMovie);
                } catch (MrsBllException ex)
                {
                    displayError(ex);
                    Logger.getLogger(MovieRecController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
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
    private void handleRateMovie(ActionEvent event)
    {
        
    }
    
}
