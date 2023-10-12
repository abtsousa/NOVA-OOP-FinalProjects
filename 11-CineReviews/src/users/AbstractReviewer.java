package users;

/**
 *  AbstractReviewer - common variables and methods between different reviewer types
 */
public abstract class AbstractReviewer extends AbstractUser implements Reviewer {
    //VARIABLE
    private int reviewCount; //Number of posted reviews

    //CONSTRUCTOR
    /**
     * Platform's registered reviewer
     * @param username user's unique identifier
     */
    AbstractReviewer(String username) {
        super(username);
        this.reviewCount = 0;
    }

    //METHODS
    @Override
    public int getCounter() {return reviewCount;}

    @Override
    public void incrementCounter() {
        reviewCount++;
    }

    @Override
    public abstract int getWeight();

    @Override
    public String toString() {
        return "User " + username + " has posted " + reviewCount + " reviews";
    }
}
