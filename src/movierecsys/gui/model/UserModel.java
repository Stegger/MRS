/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.gui.model;

import java.util.List;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableObjectValue;
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

    //Infrastructure component(s):
    private MRSLogicFacade facade;

    //View data
    private ObservableList<User> allUsers;
    private SimpleObjectProperty<User> selectedUser;

    //Cache data
    private List<User> cacheAllUsers;

    public UserModel() throws MrsBllException
    {
        facade = new MRSManager();
        allUsers = FXCollections.observableArrayList();
        cacheAllUsers = facade.getAllUsers();
        allUsers.addAll(cacheAllUsers);
        selectedUser = new SimpleObjectProperty<>();
    }

    public ObservableList<User> getAllUsers()
    {
        return allUsers;
    }

    public void searchUser(String query) throws MrsBllException
    {
        if (query != null)
        {
            List<User> results;
            if (!query.isEmpty())
            {
                results = facade.searchUsers(query);
            } else
            {
                results = cacheAllUsers;
            }
            allUsers.clear();
            allUsers.addAll(results);
        }
    }

    public ReadOnlyObjectProperty<User> getSelectedUser()
    {
        return selectedUser;
    }

    public void setSelectedUser(User user)
    {
        selectedUser.setValue(user);
    }

}
