//Success messages
enum MSG {
    EXIT("Bye!\n"),
    LIST_USERS("All registered users:\n"),
    LIST_SHOWS("All shows:\n"),

    //Messages with parameters
    //Must call them with MSG.NAME.msg in printf to be used as format strings
    //Unlike .print() .printf() doesn't call .toString() on the first argument
    USER_REGISTERED("User %s was registered as %s.\n"),
    LIST_USERS_REVIEWER("User %s has posted %d reviews\n"),
    LIST_USERS_ADMIN("Admin %s has uploaded %d shows\n"),
    MOVIE_REGISTERED("Movie %s (%d) was uploaded [%d new artists were created].\n"),
    SERIES_REGISTERED("Series %s (%d) was uploaded [%d new artists were created].\n"),
    LIST_SHOWS_EACH("%s; %s; %d; %s; %d; %s; "),
    ARTIST_REGISTERED("%s bio was created.\n"),
    ARTIST_UPDATED("%s bio was updated.\n"),
    REVIEW_REGISTERED("Review for %s was registered [%d reviews].\n"),
    LIST_REVIEWS("Reviews of %s [%.1f]:\n"),
    LIST_REVIEWS_EACH("Review of %s (%s): %s [%s]\n"),
    LIST_GENRES("Search by genre:\n"),
    LIST_SEARCH_RESULT("%s %s by %s released on %d [%.1f]\n"),
    LIST_RELEASED("Shows released on %d:\n"),
    LIST_AVOIDERS("These %d artists never worked together:\n"),
    LIST_FRIENDS("These artists have worked on %d projects together:\n"),
    LIST_FRIENDS_EACH("%s and %s\n");

    //CONSTRUCTOR
    MSG(String message) {
        this.msg = message;
    }

    //CONSTANT
    final String msg;

    //METHOD
    @Override
    public String toString() {
        return msg;
    }

}