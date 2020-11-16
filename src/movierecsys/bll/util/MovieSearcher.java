/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.bll.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieSearcher
{

    /**
     * Searches for Movies that matches the given query string
     *
     * @param searchBase
     * @param query
     * @return
     */
    public static List<Movie> search(List<Movie> searchBase, String query)
    {
        //Option A:
        List<Movie> results = new ArrayList<>(searchBase); //I create a clone of the original searchBase
        results.removeIf(movie -> !(movie.getTitle().toLowerCase().contains(query.toLowerCase()) || ("" + movie.getYear()).contains(query)));

        //Below is the same method with a different implementation:
        /*for (Movie movie : searchBase)
        {
            if (movie.getTitle().toLowerCase().contains(query.toLowerCase()) || ("" + movie.getYear()).contains(query))
            {
                results.add(movie);
            }
        }*/

        return results;
    }

}
