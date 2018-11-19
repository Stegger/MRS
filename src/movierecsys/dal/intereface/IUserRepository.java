/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.intereface;

import java.util.List;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public interface IUserRepository
{

    /**
     * Gets a list of all known users.
     * @return List of users.
     */
    List<User> getAllUsers();

    /**
     * Gets a single User by its ID.
     * @param id The ID of the user.
     * @return The User with the ID.
     */
    User getUser(int id);

    /**
     * Updates a user so the persistence storage reflects the given User object.
     * @param user The updated user.
     */
    void updateUser(User user);
    
}
