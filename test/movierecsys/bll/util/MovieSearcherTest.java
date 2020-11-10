/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.bll.util;

import movierecsys.be.Movie;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;


/**
 * @author pgn
 */
public class MovieSearcherTest {

    public MovieSearcherTest() {
    }

    /**
     * Test of search method, of class MovieSearcher.
     */
    @Test
    public void testSearch() {
        System.out.println("search");
        List<Movie> searchBase = new ArrayList<>();
        Movie expectedResult = new Movie(1, 1994, "Shawshank Redemption");
        searchBase.add(expectedResult);
        searchBase.add(new Movie(2, 2004, "I Robot"));
        searchBase.add(new Movie(3, 1999, "Pirates of Silicon Valley"));

        String query = "red";
        MovieSearcher instance = new MovieSearcher();
        List<Movie> result = instance.search(searchBase, query);

        Assertions.assertNotNull(result, "Assert that the search method returns an object");
        Assertions.assertTrue(result.size() == 1, "Search for \"red\" should return one result");
        Assertions.assertTrue(result.get(0).getId() == 1, "Search for \"red\" should return result with ID: 1");


        query = "Red";
        result = instance.search(searchBase, query);

        Assertions.assertNotNull(result, "Assert that the search method returns an object");
        Assertions.assertTrue(result.size() == 1, "Search for \"Red\" should return one result");
        Assertions.assertTrue(result.get(0).getId() == 1, "Search for \"Red\" should return result with ID: 1");

        query = "ir";
        result = instance.search(searchBase, query);

        Assertions.assertNotNull(result, "Assert that the search method returns an object");
        Assertions.assertTrue(result.size() == 2, "Search for \"ir\" should return two results");

        query = "qwerty";
        result = instance.search(searchBase, query);

        Assertions.assertNotNull(result, "Assert that the search method returns an object");
        Assertions.assertTrue(result.isEmpty(), "Assert that no results are found for search \"qwerty\"");
    }

}
