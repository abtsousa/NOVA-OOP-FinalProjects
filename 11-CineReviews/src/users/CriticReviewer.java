package users;

/**
 * CriticReviewer - user who may review shows with more significance
 */
public class CriticReviewer extends AbstractReviewer implements Reviewer {
    //CONSTANT
    private static final int WEIGHT = 5; //Score point multiplier

    //CONSTRUCTOR
    /**
     * User who may review shows with more significance
     * @param username user's unique identifier
     */
    public CriticReviewer(String username) {
        super(username);
    }

    //METHODS
    @Override
    public UserType getType() {
        return UserType.critic;
    }

    @Override
    public int getWeight() {
        return WEIGHT;
    }

}
