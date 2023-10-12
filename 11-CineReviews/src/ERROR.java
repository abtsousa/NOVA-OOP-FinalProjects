//Error messages
enum ERROR {
    UNKNOWN_COMMAND("Unknown command. Type help to see available commands.\n"),
    UNKNOWN_USER_TYPE("Unknown user type!\n"),
    NO_USERS("No users registered.\n"),
    INVALID_PASSWORD("Invalid authentication!\n"),
    NO_SHOWS("No shows have been uploaded.\n"),
    SHOW_CRITERIA_NOT_FOUND("No show was found within the criteria.\n"),
    NO_ARTISTS("No artists yet!\n"),
    NO_AVOIDERS("It is a small world!\n"),
    NO_FRIENDS("No collaborations yet!\n"),

    //Messages with parameters
    //Must call them with ERROR.NAME.msg in printf to be used as format strings
    //Unlike .print() .printf() doesn't call .toString() on the first argument
    USER_ALREADY_EXISTS("User %s already exists!\n"),
    ADMIN_NOT_FOUND("Admin %s does not exist!\n"),
    SHOW_ALREADY_EXISTS("Show %s already exists!\n"),
    BIO_ALREADY_EXISTS("Bio of %s is already available!\n"),
    ARTIST_NOT_FOUND("No information about %s!\n"),
    USER_NOT_FOUND("User %s does not exist!\n"),
    USER_NOT_REVIEWER("Admin %s cannot review shows!\n"),
    SHOW_NOT_FOUND("Show %s does not exist!\n"),
    SHOW_ALREADY_REVIEWED("%s has already reviewed %s!\n"),
    NO_REVIEWS("Show %s has no reviews.\n");

    //CONSTANT
    final String msg;

    //CONSTRUCTOR
    ERROR(String message) {
        this.msg = message;
    }

    //METHOD
    @Override
    public String toString() {
        return msg;
    }

}