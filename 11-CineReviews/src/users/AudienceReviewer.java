package users;

/**
 * AudienceReviewer - user who may review shows
 */
public class AudienceReviewer extends AbstractReviewer implements Reviewer {
    //CONSTANT
    private static final int WEIGHT = 1; //Score point multiplier

    //CONSTRUCTOR
    /**
     * User who may review shows
     * @param username user's unique identifier
     */
    public AudienceReviewer(String username) {
        super(username);
    }

    //METHODS
    @Override
    public UserType getType() {
        return UserType.audience;
    }

    @Override
    public int getWeight() {
        return WEIGHT;
    }

}
