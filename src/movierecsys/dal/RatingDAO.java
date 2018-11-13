/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import movierecsys.be.Rating;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class RatingDAO
{

    private static final String RATING_SOURCE = "data/ratings.txt";

    /**
     * Persists the given rating.
     *
     * @param rating the rating to persist.
     */
    public void createRating(Rating rating)
    {
        //TODO Rate movie
    }

    /**
     * Updates the rating to reflect the given object.
     *
     * @param rating The updated rating to persist.
     * @throws java.io.IOException
     */
    public void updateRating(Rating rating) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(RATING_SOURCE, "rw"))
        {
            do
            {
                String line = raf.readLine();
                Rating r = getRatingFromLine(line);
                if (r.getMovie() == rating.getMovie() && r.getUser() == rating.getUser())
                {
                    if (rating.getRating() != r.getRating())
                    {
                        raf.seek(raf.getFilePointer() - line.getBytes().length);
                        String updateLine = rating.getMovie() + "," + rating.getUser() + "," + rating.getRating() + "\n";
                        raf.writeChars(line);
                    }
                    return;
                }
            } while (raf.getFilePointer() < raf.length());
        }
    }

    /**
     * Removes the given rating.
     *
     * @param rating
     */
    public void deleteRating(Rating rating)
    {
        //TODO Delete rating
    }

    /**
     * Gets all ratings from all users.
     *
     * @return List of all ratings.
     */
    public List<Rating> getAllRatings()
    {
        //TODO Get all rating.
        return null;
    }

    /**
     * Get all ratings from a specific user.
     *
     * @param user The user
     * @return The list of ratings.
     */
    public List<Rating> getRatings(User user)
    {
        //TODO Get user ratings.
        return null;
    }

    private Rating getRatingFromLine(String line) throws NumberFormatException
    {
        String[] cols = line.split(",");
        int movId = Integer.parseInt(cols[0]);
        int userId = Integer.parseInt(cols[1]);
        int rating = Integer.parseInt(cols[2]);
        return new Rating(movId, userId, rating);
    }

}
