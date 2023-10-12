package users;

/**
 * SuperUser Interface:
 * Methods that manage users with priviligied abilities
 */
public interface SuperUser extends User {

    /**
     * Checks if input password is corret
     * @param password input password
     * @return true if it's the correct password
     */
    boolean passwordCheck(String password);
}