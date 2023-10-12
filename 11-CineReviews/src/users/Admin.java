package users;

/**
 * Admin - user that is responsible for adding information about the shows and artists
 */
public class Admin extends AbstractUser implements SuperUser {
    //VARIABLES
    private final String password; //User's password
    private int showCount; //User's number of shows uploaded

    //CONSTRUCTOR

    /**
     * User Admin that is responsible for adding information about the shows and artists
     * @param username admin's unique identifier
     * @param password admin's password
     */
    public Admin(String username, String password) {
        super(username);
        this.password = password;
        this.showCount = 0;
    }

    @Override
    public UserType getType() {
        return UserType.admin;
    }

    @Override
    public int getCounter() {return showCount;}

    @Override
    public void incrementCounter() {showCount++;}

    @Override
    public boolean passwordCheck(String password)  {
        return password.equals(this.password);
    }
}
