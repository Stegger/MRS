/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import movierecsys.dal.intereface.IUserRepository;

import java.util.List;

import movierecsys.be.User;
import movierecsys.dal.exception.MrsDalException;

/**
 * @author pgn
 */
public class UserDAO implements IUserRepository {

    private static final String USER_SOURCE = "data/users.txt";

    /**
     * Gets a list of all known users.
     *
     * @return List of users.
     */
    @Override
    public List<User> getAllUsers() throws MrsDalException {
        List<User> allUser = new ArrayList<>();
        File file = new File(USER_SOURCE);
        try {
            for (String line : Files.readAllLines(file.toPath())) {

                if (!line.isEmpty()) //If we have an empty line in the file we will skip it
                {
                    try {
                        String[] arrUser = line.split(",");
                        int id = Integer.parseInt(arrUser[0].trim());
                        String name = arrUser[1].trim();
                        User user = new User(id, name);
                        allUser.add(user);
                    } catch (Exception ex) {
                        //Do nothing. Optimally we would log the error.
                    }
                }
            }
        } catch (IOException e) {

            e.printStackTrace();
            throw new MrsDalException("Could not read all users from disk.");
        }
        return allUser;
    }

    /**
     * Gets a single User by its ID.
     *
     * @param id The ID of the user.
     * @return The User with the ID.
     */
    @Override
    public User getUser(int id) throws MrsDalException {
        List<User> allUsers = getAllUsers();
        for (User user : allUsers) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new MrsDalException("User with id " + id + " not found.");
    }

    /**
     * Updates a user so the persistence storage reflects the given User object.
     *
     * @param user The updated user.
     */
    @Override
    public void updateUser(User user) throws MrsDalException {
        //TODO Do update user.
    }

}
