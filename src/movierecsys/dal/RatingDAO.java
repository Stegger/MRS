/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import movierecsys.be.Rating;
import movierecsys.be.User;

/**
 *
 * @author pgn
 */
public class RatingDAO
{

    private static final String RATING_SOURCE = "data/user_ratings";

    private static final int RECORD_SIZE = Integer.BYTES * 3;

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
     * Updates the rating to reflect the given object. Assumes that the source file is order by movie ID, then User ID..
     *
     * @param rating The updated rating to persist.
     * @throws java.io.IOException
     */
    public void updateRating(Rating rating) throws IOException
    {
        try (RandomAccessFile raf = new RandomAccessFile(RATING_SOURCE, "rw"))
        {
            long totalRatings = raf.length();
            long low = 0;
            long high = totalRatings - 1;
            while (high >= low) //Binary search of movie ID
            {
                long pos = (high + low) / 2;
                raf.seek(pos);
                int movId = raf.readInt();

                if (rating.getMovie() < movId) //We did not find the movie.
                {
                    high = pos - RECORD_SIZE; //We half our problem size to the upper half.
                } else if (movId > rating.getMovie()) //We did not find the movie.
                {
                    low = pos + RECORD_SIZE; //We half our problem size (Just the lower half)
                } else
                {
                    //Since user id's are unsorted we search liniarily in both direction for the user ID.
                    boolean inRange = true;
                    int offSet = 0;
                    while (inRange)
                    {
                        raf.seek(pos + offSet);
                        int movieID = raf.readInt();
                        int userID = raf.readInt();
                        if (movId == rating.getMovie() && userID == rating.getUser())
                        {
                            System.out.println("Found: " + movId + "," + userID);
                            raf.writeInt(rating.getRating());
                            return;
                        }
                        if (movId != rating.getMovie())
                        {
                            inRange = false;
                            break;
                        }
                        offSet += RECORD_SIZE;
                    }
                    inRange = true;
                    offSet = 0 - RECORD_SIZE;
                    while (inRange)
                    {
                        raf.seek(pos);
                        int movieID = raf.readInt();
                        int userID = raf.readInt();
                        if (movId == rating.getMovie() && userID == rating.getUser())
                        {
                            System.out.println("Found: " + movId + "," + userID);
                            raf.writeInt(rating.getRating());
                            return;
                        }
                        if (movId != rating.getMovie())
                        {
                            inRange = false;
                            break;
                        }
                        offSet -= RECORD_SIZE;
                    }
                }
            }
        }
        throw new IllegalArgumentException("Rating not found, can't update");
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
    public List<Rating> getAllRatings() throws IOException
    {
        List<Rating> allRatings = new ArrayList<>();
        byte[] all = Files.readAllBytes(new File(RATING_SOURCE).toPath()); //I get all records as binary data!
        for (int i = 0; i < all.length; i += RECORD_SIZE)
        {
            int movieId = ByteBuffer.wrap(all, i, Integer.BYTES).order(ByteOrder.BIG_ENDIAN).getInt();
            int userId = ByteBuffer.wrap(all, i + Integer.BYTES, Integer.BYTES).order(ByteOrder.BIG_ENDIAN).getInt();
            int rating = ByteBuffer.wrap(all, i + Integer.BYTES * 2, Integer.BYTES).order(ByteOrder.BIG_ENDIAN).getInt();
            Rating r = new Rating(movieId, userId, rating);
            allRatings.add(r);
        }
        Collections.sort(allRatings, (Rating o1, Rating o2) ->
        {
            int movieCompare = Integer.compare(o1.getMovie(), o2.getMovie());
            return movieCompare == 0 ? Integer.compare(o1.getUser(), o2.getUser()) : movieCompare;
        });
        return allRatings;
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
