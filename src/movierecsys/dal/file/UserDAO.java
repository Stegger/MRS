/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.file;

import movierecsys.dal.intereface.IUserRepository;
import java.util.List;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class UserDAO implements IUserRepository
{
    private static final String USER_SOURCE = "data/users.txt";
    
    /**
     * Gets a list of all known users.
     * @return List of users.
     */
    @Override
    public List<User> getAllUsers()
    {
        //TODO Get all users
        return null;
    }
    
    /**
     * Gets a single User by its ID.
     * @param id The ID of the user.
     * @return The User with the ID.
     */
    @Override
    public User getUser(int id)
    {
        //TODO Get User
        return null;
    }
    
    /**
     * Updates a user so the persistence storage reflects the given User object.
     * @param user The updated user.
     */
    @Override
    public void updateUser(User user)
    {
        //TODO Update user
    }
    
}
