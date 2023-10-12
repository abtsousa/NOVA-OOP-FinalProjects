package reviews;

import users.User;

/**
 * Review Interface:
 * Methods that manage the review
 */
public interface Review {

    /**
     * Returns the review's author
     * @return review's author
     */
    User getReviewer();

    /**
     * Returns the review's commment
     * @return review's commment
     */
    String getComment();

    /**
     * Returns the review's classification
     * @return review's classification
     */
    String getClassification();

    /**
     * Returns the review's score
     * @return review's score
     */
    int getScore();

    /**
     * Returns the review's score point multiplier
     * @return review's score point multiplier
     */
    int getWeight();

}
