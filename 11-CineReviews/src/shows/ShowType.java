package shows;

/**
 * Lists all available show types
 * On a separate file, so it can be accessed from outside the package
 */
public enum ShowType {
    Movie, Series;

    //METHOD
    /**
     * Modifies all characters to lower case
     * @return all characters to lower case
     */
    public String toLower() {
        return this.toString().toLowerCase();
    }
}

