package users;

/**
 * User Interface:
 * Methods that manage user objects
 */
public interface User extends Comparable<User>{

    /**
     * Returns user's unique identifier
     * @return user's unique identifier
     */
    String getName();

    /**
     * Returns user's user type
     * @return user's user type
     */
    UserType getType();

    /**
     * Returns number of reviews posted
     * @return number of reviews posted
     */
    int getCounter();

    /**
     * Increments number of reviews posted
     */
    void incrementCounter();

    /**
     * Compares two user objects
     * @param other the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object is less than,
     * equal to, or greater than the specified object.
     */
    int compareTo(User other);
}
