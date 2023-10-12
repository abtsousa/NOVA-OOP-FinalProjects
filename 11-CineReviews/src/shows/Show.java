package shows;

import artists.*;
import exceptions.EmptyReviewsException;
import exceptions.ReviewAlreadyExistsException;
import reviews.Review;
import users.Reviewer;

import java.util.*;

/**
 * Show Interface:
 * Methods that manage the show
 */
public interface Show {

    //GET
    /**
     * Returns the show's title
     * @return show's title
     */
    String getTitle();

    /**
     * Returns the show's director || creator
     * @return show's director || creator
     */
    Artist getShowrunner();

    /**
     * Returns the show's duration || number of seasons
     * @return show's duration || number of seasons
     */
    int getLength();

    /**
     * Returns the show's age certification
     * @return show's age certification
     */
    String getAgeCert();

    /**
     * Returns the show's release date
     * @return show's release date
     */
    int getReleaseDate();

    /**
     * Returns the show's genre(s)
     * @return show's genre(s)
     */
    List<String> getGenres();

    /**
     * Returns the show's top cast
     * @return show's top cast
     */
    List<Artist> getTopCast();

    /**
     * Returns the show's set of pairs of artists who worked together
     * @return show's set of pairs of artists who worked together
     */
    Set<Pair> getCollaborators();

    /**
     * Returns the show's type
     * @return show's type
     */
    ShowType getShowType();

    /**
     * Returns the show's score
     * @return show's score
     */
    double getScore();

    /**
     * Returns the number of show's reviews
     * @return number of show's reviews
     */
    int getCountReviews();

    //LOGIC
    /**
     * Adds a review to this show
     * @param user user who posted this review
     * @param review review being posted
     * @throws ReviewAlreadyExistsException if this review was already posted
     */
    void addReview(Reviewer user, Review review) throws ReviewAlreadyExistsException;

    //LIST

    //LOGIC
    /**
     * Lists this show's reviews
     * @return list of this show's reviews
     * @throws EmptyReviewsException if the show has no reviews
     */
    Iterator<Review> listReviews() throws EmptyReviewsException;

}
