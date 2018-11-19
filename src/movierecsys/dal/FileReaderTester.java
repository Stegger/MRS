/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import movierecsys.dal.file.RatingDAO;
import movierecsys.dal.file.MovieDAO;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.be.Rating;

/**
 *
 * @author pgn
 */
public class FileReaderTester
{

    /**
     * Example method. This is the code I used to create the users.txt files.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException
    {
        mitigateMovies();

    }

    public static void mitigateMovies() throws IOException
    {
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setServerName("10.176.111.31");
        ds.setDatabaseName("mrs");
        ds.setUser("CS2018A_40");
        ds.setPassword("CS2018A_40");

        MovieDAO mvDao = new MovieDAO();
        List<Movie> movies = mvDao.getAllMovies();

        try (Connection con = ds.getConnection())
        {
            Statement statement = con.createStatement();
            
            for (Movie movie : movies)
            {
                String sql = "INSERT INTO Movie (id,year,title) VALUES("
                        + movie.getId() + ","
                        + movie.getYear() + ",'"
                        + movie.getTitle().replace("'", "") + "');";
                System.out.println(sql);
                int i = statement.executeUpdate(sql);
                // INSERT INTO Movie (id,year,title) VALUES (1,2018,Venom);
                System.out.println("Affected row = " + i);
            }
        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

    public static void createRafFriendlyRatingsFile() throws IOException
    {
        String target = "data/user_ratings";
        RatingDAO ratingDao = new RatingDAO();
        List<Rating> all = ratingDao.getAllRatings();

        try (RandomAccessFile raf = new RandomAccessFile(target, "rw"))
        {
            for (Rating rating : all)
            {
                raf.writeInt(rating.getMovie());
                raf.writeInt(rating.getUser());
                raf.writeInt(rating.getRating());
            }
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

}
