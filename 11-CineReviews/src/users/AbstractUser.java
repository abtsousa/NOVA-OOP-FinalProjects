package users;

/**
 * AbstractUser - common variables and methods between different user types
 */
public abstract class AbstractUser implements User {
    //VARIABLE
    final String username; //User's unique identifier

    //CONSTRUCTOR

    /**
     * Platform's registered user
     * @param username user's unique identifier
     */
    AbstractUser(String username) {
        this.username = username;
    }

    //METHODS
    @Override
    public String getName() {
        return username;
    }

    @Override
    public int compareTo(User other) {
        return username.compareTo(other.getName());
    }

}
