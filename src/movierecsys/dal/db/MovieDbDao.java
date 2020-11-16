/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal.db;

import movierecsys.dal.intereface.IMovieRepository;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import movierecsys.be.Movie;
import movierecsys.dal.exception.MrsDalException;

/**
 *
 * @author pgn
 */
public class MovieDbDao implements IMovieRepository
{

    private final JDBCConnectionPool connectionPool;

    public MovieDbDao() throws IOException
    {
        connectionPool = JDBCConnectionPool.getInstance();
    }

    @Override
    public Movie createMovie(int releaseYear, String title) throws MrsDalException
    {
        String sql = "INSERT INTO Movie (year,title) VALUES(?,?);";
        Connection con = connectionPool.checkOut(); // <<< Using the object pool here <<<
        try (PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            st.setInt(1, releaseYear);
            st.setString(2, title);
            st.executeUpdate();
            ResultSet rs = st.getGeneratedKeys();
            int id = 0;
            if (rs.next())
            {
                id = rs.getInt(1);
            }
            Movie movie = new Movie(id, releaseYear, title);
            return movie;
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new MrsDalException("Could not create movie.", ex);
        } finally
        {
            connectionPool.checkIn(con);// <<< Using the object pool here <<<
        }
    }

    @Override
    public void deleteMovie(Movie movie) throws MrsDalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Movie> getAllMovies() throws MrsDalException
    {
        List<Movie> movies = new ArrayList<>();
        Connection con = connectionPool.checkOut();
        try (Statement statement = con.createStatement();)
        {
            ResultSet rs = statement.executeQuery("SELECT * FROM Movie;");
            while (rs.next())
            {
                int id = rs.getInt("id");
                int year = rs.getInt("year");
                String title = rs.getString("title");
                Movie movie = new Movie(id, year, title);
                movies.add(movie);
            }
            return movies;
        } catch (SQLException ex)
        {
            throw new MrsDalException("Could not get all movies from database", ex);
        } finally
        {
            connectionPool.checkIn(con);
        }
    }

    @Override
    public Movie getMovie(int id) throws MrsDalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateMovie(Movie movie) throws MrsDalException
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
