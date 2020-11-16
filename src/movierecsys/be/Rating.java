/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movierecsys.be;

/**
 *
 * @author pgn
 */
public class Rating
{

    public static final int SUPER_BAD = -5;
    public static final int BAD = -3;
    public static final int NEUTRAL = 1;
    public static final int GOOD = 3;
    public static final int SUPER_GOOD = 5;

    private final int movieId;
    private final int userId;
    private int rating;

    /**
     * Constructs a new rating.
     *
     * @param movie The movie being rated.
     * @param user The rating user.
     * @param rating The value of the rating. Only the constants of the Rating
     * class are allowed values.
     */
    public Rating(int movieId, int userId, int rating)
    {
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
    }

    /**
     * Sets the rating to a new value. Only the constants of the Rating class
     * are allowed values.
     *
     * @param rating The rating to set.
     */
    public void setRating(int rating)
    {
        this.rating = rating;
    }

    /**
     * Gets the movie being rated.
     *
     * @return A movie
     */
    public int getMovie()
    {
        return movieId;
    }

    /**
     * Gets the rating user.
     *
     * @return A user.
     */
    public int getUser()
    {
        return userId;
    }

    /**
     * Gets the rating value.
     *
     * @return An integer.
     */
    public int getRating()
    {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;

        Rating rating = (Rating) o;

        if (movieId != rating.movieId) return false;
        return userId == rating.userId;
    }

    @Override
    public int hashCode() {
        int result = movieId;
        result = 31 * result + userId;
        return result;
    }

    @Override
    public String toString() {
        return "Rating{" +
                "movieId=" + movieId +
                ", userId=" + userId +
                ", rating=" + rating +
                '}';
    }
}
