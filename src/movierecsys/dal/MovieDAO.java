/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import movierecsys.be.Movie;

/**
 *
 * @author pgn
 */
public class MovieDAO
{

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     * @throws java.io.IOException 
     */
    public List<Movie> getAllMovies() throws IOException
    {
        List<Movie> allMovies = new ArrayList<>();
        String source = "data/movie_titles.txt";
        File file = new File(source);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) //Using a try with resources!
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (!line.isEmpty())
                {
                    try
                    {
                        Movie mov = stringArrayToMovie(line);
                        allMovies.add(mov);
                    } catch (Exception ex)
                    {
                        //Do nothing. Optimally we would log the error.
                    }
                }
            }
        }
        return allMovies;
    }

    /**
     * Reads a movie from the comma separated line.
     *
     * @param line the comma separated line.
     * @return The representing Movie object.
     * @throws NumberFormatException
     */
    private Movie stringArrayToMovie(String line)
    {
        String[] arrMovie = line.split(",");

        int id = Integer.parseInt(arrMovie[0]);
        int year = Integer.parseInt(arrMovie[1]);
        String title = arrMovie[2];

        Movie mov = new Movie(id, year, title);
        return mov;
    }

    /**
     * Creates a movie in the persistence storage.
     *
     * @param releaseYear The release year of the movie
     * @param title The title of the movie
     * @return The object representation of the movie added to the persistence
     * storage.
     */
    public Movie createMovie(int releaseYear, String title) throws IOException
    {
        Path path = new File(MOVIE_SOURCE).toPath();
        int id = -1;
        try (BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.SYNC, StandardOpenOption.APPEND, StandardOpenOption.WRITE))
        {
            id = getNextAvailableMovieID();
            bw.newLine();
            bw.write(id + "," + releaseYear + "," + title);
        }
        return new Movie(id, releaseYear, title);
    }

    /**
     * Examines all stored movies and returns the next available highest ID.
     *
     * @return
     * @throws IOException
     */
    private int getNextAvailableMovieID() throws IOException
    {
        List<Movie> allMovies = getAllMovies();
        int highId = allMovies.get(allMovies.size() - 1).getId();
        return highId + 1;
    }

    /**
     * Deletes a movie from the persistence storage.
     *
     * @param movie The movie to delete.
     */
    private void deleteMovie(Movie movie)
    {
        
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object. 
     *
     * @param movie The updated movie.
     */
    public void updateMovie(Movie movie) throws IOException
    {
        File tmp = new File("data/tmp_movies.txt");
        List<Movie> allMovies = getAllMovies();
        allMovies.removeIf((Movie t) -> t.getId() == movie.getId());
        allMovies.add(movie);
        Collections.sort(allMovies, (Movie o1, Movie o2) -> Integer.compare(o1.getId(), o2.getId()));
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(tmp)))
        {
            for (Movie mov : allMovies)
            {
                bw.write(mov.getId()+","+mov.getYear()+","+mov.getTitle());
                bw.newLine();
            }
        }
        Files.copy(tmp.toPath(), new File(MOVIE_SOURCE).toPath(), StandardCopyOption.REPLACE_EXISTING);
        Files.delete(tmp.toPath());
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    private Movie getMovie(int id)
    {
        //TODO Get one Movie
        return null;
    }

}
