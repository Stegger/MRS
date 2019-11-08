/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import movierecsys.be.User;
import movierecsys.bll.MRSLogicFacade;
import movierecsys.bll.MRSManager;
import movierecsys.bll.exception.MrsBllException;

/**
 *
 * @author pgn
 */
public class UserModel
{

    private MRSLogicFacade facade;

    private ObservableList<User> allUsers;

    public UserModel() throws MrsBllException
    {
        facade = new MRSManager();
        allUsers = FXCollections.observableArrayList();
        allUsers.addAll(facade.getAllUsers());
    }

    public ObservableList<User> getAllUsers()
    {
        return allUsers;
    }

    public void searchUser(String query)
    {
        if (query != null)
        {
            List<User> results;
            if (!query.isEmpty())
            {
                results = facade.searchUsers(query);
            } else
            {
                results = facade.getAllUsers();
            }
            allUsers.clear();
            allUsers.addAll(results);
        }
    }

    public void setSelectedUser(User newUser)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
