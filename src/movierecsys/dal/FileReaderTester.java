/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.dal;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
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
        RatingDAO ratingDao = new RatingDAO();
        Rating rat = new Rating(17742, 2631660, 3);
        ratingDao.updateRating(rat);
    }

    public static void createRafFriendlyRatingsFile() throws IOException
    {
        String source = "data/ratings.txt";
        String target = "data/user_ratings";

        try (RandomAccessFile raf = new RandomAccessFile(target, "rw"))
        {
            Files.lines(new File(source).toPath()).forEach((String t) ->
            {
                try
                {
                    String[] cols = t.split(",");
                    int movId = Integer.parseInt(cols[0]);
                    int userId = Integer.parseInt(cols[1]);
                    int rating = Integer.parseInt(cols[2]);
                    raf.writeInt(movId);
                    raf.writeInt(userId);
                    raf.writeInt(rating);
                } catch (IOException ex)
                {
                    System.out.println(ex.getMessage());
                    ex.printStackTrace();
                }
            });
        }
    }

}
