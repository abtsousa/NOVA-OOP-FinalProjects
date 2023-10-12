//Commands

enum CMD {
    REGISTER("register","registers a user in the system"),
    USERS("users","lists all registered users"),
    MOVIE("movie","uploads a new movie"),
    SERIES("series","uploads a new series"),
    SHOWS("shows","lists all shows"),
    ARTIST("artist","adds bio information about an artist"),
    CREDITS("credits","lists the bio and credits of an artist"),
    REVIEW("review","adds a review to a show"),
    REVIEWS("reviews","lists the reviews of a show"),
    GENRE("genre","lists shows of given genres"),
    RELEASED("released","lists shows released in a given year"),
    AVOIDERS("avoiders","lists artists that have no common projects"),
    FRIENDS("friends","lists artists that have more projects together"),
    HELP("help","shows the available commands"),
    EXIT("exit","terminates the execution of the program"),
    UNKNOWN(null,null); //returned if input is invalid

    //CONSTANTS
    private final String input;
    private final String description;

    //CONSTRUCTOR
    CMD(String input, String description) {
        this.input = input;
        this.description = description;
    }

    //METHOD
    @Override
    public String toString() {
        return input + " - " + description;
    }
}