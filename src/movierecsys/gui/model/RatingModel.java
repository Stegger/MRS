/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.model;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movierecsys.be.Movie;
import movierecsys.be.User;
import movierecsys.bll.MRSLogicFacade;
import movierecsys.bll.MRSManager;
import movierecsys.bll.exception.MrsBllException;
import movierecsys.dal.exception.MrsDalException;

import java.io.IOException;
import java.util.List;

/**
 * @author pgn
 */
public class RatingModel {

    private SimpleObjectProperty<User> selectedUser;

    private ObservableList<Movie> recommendedMovies;

    private MRSLogicFacade logicFacade;

    public RatingModel() throws MrsBllException {
        selectedUser = new SimpleObjectProperty<>();
        recommendedMovies = FXCollections.observableArrayList();
        logicFacade = new MRSManager();
        recommendedMovies.addAll(logicFacade.getAllTimeTopRatedMovies());

    }

    public void setUser(ObservableValue<User> user)  {
        selectedUser.bind(user);

        selectedUser.addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {

                try {
                    if (user != null) {
                        List<Movie> recommendations = logicFacade.getMovieReccomendations(user);
                        recommendedMovies.clear();
                        recommendedMovies.addAll(recommendations);
                    }
                } catch (MrsBllException e) {
                    Platform.runLater(() -> {
                        System.out.println("Yikes");
                    });
                    e.printStackTrace();
                }
            }
        });


    }

    public ObservableList<Movie> getRecommendedMovies() {
        return recommendedMovies;
    }
}
