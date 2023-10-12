package users;

/**
 * Reviewer Interface:
 * Methods that manage user's reviews
 */
public interface Reviewer extends User {

    /**
     * Returns score point multiplier
     * @return score point multiplier
     */
    int getWeight();
}
