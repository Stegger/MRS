/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.file;

import movierecsys.dal.intereface.IMovieRepository;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.dal.exception.MrsDalException;

/**
 *
 * @author pgn
 */
public class MovieDAO implements IMovieRepository
{

    private static final String MOVIE_SOURCE = "data/movie_titles.txt";

    /**
     * Gets a list of all movies in the persistence storage.
     *
     * @return List of movies.
     * @throws java.io.IOException
     */
    @Override
    public List<Movie> getAllMovies() throws MrsDalException
    {
        List<Movie> allMovies = new ArrayList<>();
        String source = "data/movie_titles.txt";
        File file = new File(source);

        try ( BufferedReader reader = new BufferedReader(new FileReader(file))) //Using a try with resources!
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
                        //Bad read. Should be logged and/or displayed
                    }
                }
            }
        } catch (IOException ex)
        {
            throw new MrsDalException("Could not read all files from disk", ex);
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
        // Add if commas in title, includes the rest of the string
        for (int i = 3; i < arrMovie.length; i++)
        {
            title += "," + arrMovie[i];
        }
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
    @Override
    public Movie createMovie(int releaseYear, String title) throws MrsDalException
    {
        Path path = new File(MOVIE_SOURCE).toPath();
        int id = -1;
        try ( BufferedWriter bw = Files.newBufferedWriter(path, StandardOpenOption.SYNC, StandardOpenOption.APPEND, StandardOpenOption.WRITE))
        {
            id = getNextAvailableMovieID();
            bw.newLine();
            bw.write(id + "," + releaseYear + "," + title);
        } catch (IOException ex)
        {
            throw new MrsDalException("Could not create Movie.", ex);
        }
        return new Movie(id, releaseYear, title);
    }

    /**
     * Examines all stored movies and returns the next available highest ID.
     *
     * @return
     * @throws IOException
     */
    private int getNextAvailableMovieID() throws MrsDalException
    {
        List<Movie> allMovies = getAllMovies();
        if (allMovies == null || allMovies.isEmpty())
        {
            return 1;
        }
        allMovies.sort((Movie arg0, Movie arg1) -> arg0.getId() - arg1.getId());
        int id = allMovies.get(0).getId();
        for (int i = 0; i < allMovies.size(); i++)
        {
            if (allMovies.get(i).getId() <= id)
            {
                id++;
            } else
            {
                return id;
            }
        }
        return id;
    }

    /**
     * Deletes a movie from the persistence storage. Does it by overwriting the
     * file with all movies, without adding the movie we want to delete.
     *
     * @param movie The movie to delete.
     */
    @Override
    public void deleteMovie(Movie movie) throws MrsDalException
    {
        try
        {
            File file = new File(MOVIE_SOURCE);
            List<Movie> movies = getAllMovies();
            OutputStream os = Files.newOutputStream(file.toPath(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
            try ( BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os)))
            {
                for (Movie mov : movies)
                {
                    if (!mov.equals(movie))
                    {
                        String line = mov.getId() + "," + mov.getYear() + "," + mov.getTitle();
                        bw.write(line);
                        bw.newLine();
                    }
                }
            }
        } catch (IOException ex)
        {
            throw new MrsDalException("Could not delete movie.", ex);
        }
    }

    /**
     * Updates the movie in the persistence storage to reflect the values in the
     * given Movie object.
     *
     * @param movie The updated movie.
     */
    @Override
    public void updateMovie(Movie movie) throws MrsDalException
    {
        try
        {
            File tmp = new File(movie.hashCode() + ".txt"); //Creates a temp file for writing to.
            List<Movie> allMovies = getAllMovies();
            allMovies.removeIf((Movie t) -> t.getId() == movie.getId());
            allMovies.add(movie);

            //I'll sort the movies by their ID's
            allMovies.sort(new Comparator<Movie>()
            {
                @Override
                public int compare(Movie o1, Movie o2)
                {
                    return Integer.compare(o1.getId(), o2.getId());
                }
            });

            try ( BufferedWriter bw = new BufferedWriter(new FileWriter(tmp)))
            {
                for (Movie mov : allMovies)
                {
                    bw.write(mov.getId() + "," + mov.getYear() + "," + mov.getTitle());
                    bw.newLine();
                }
            }
            //Overwrite the movie file wit the tmp one.
            Files.copy(tmp.toPath(), new File(MOVIE_SOURCE).toPath(), StandardCopyOption.REPLACE_EXISTING);
            //Clean up after the operation is done (Remve tmp)
            Files.delete(tmp.toPath());
        } catch (IOException ex)
        {
            throw new MrsDalException("Could not update movie.", ex);
        }
    }

    /**
     * Gets a the movie with the given ID.
     *
     * @param id ID of the movie.
     * @return A Movie object.
     */
    @Override
    public Movie getMovie(int id) throws MrsDalException
    {
        List<Movie> all = getAllMovies();

        int index = Collections.binarySearch(all, new Movie(id, 0, ""), new Comparator<Movie>()
        {
            @Override
            public int compare(Movie o1, Movie o2)
            {
                return Integer.compare(o1.getId(), o2.getId());
            }
        });

        if (index >= 0)
        {
            return all.get(index);
        } else
        {
            throw new IllegalArgumentException("No movie with ID: " + id + " is found.");
        }
    }

}
