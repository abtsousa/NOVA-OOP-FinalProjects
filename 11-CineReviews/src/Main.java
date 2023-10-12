/**
 * An online review application
 *  @author Afonso Brás Sousa (LEI n.º 65263)
 *  @author Alexandre Cristóvão (LEI n.º 65143)
 */

import java.util.*;

import artists.*;
import exceptions.*;
import reviews.*;
import shows.*;
import users.*;

/**
 * Main Class
 * Executes the CineReviews platform
 */
public class Main {

    /**
     * Main program
     * Executes the CineReviews platform
     * Manages user input and program output
     * @param args - arguments to execute the app. None are used.
     */
    public static void main(String[] args) {
        CineReviewsInterface cr = new CineReviews();
        Scanner input = new Scanner(System.in);
        processCommands(cr,input);
        input.close();
    }

    //METHODS
    //USER COMMAND MANAGEMENT

    private static CMD getCommand(Scanner in) {
        String command = in.next().toUpperCase();
        try {
            return CMD.valueOf(command);
        } catch (IllegalArgumentException e) {
            return CMD.UNKNOWN;
        }
    }

    private static void processCommands(CineReviewsInterface cr, Scanner in)   {
        CMD command;
        do {
            command = getCommand(in);
            processCommand(cr, command, in);
        } while (!command.equals(CMD.EXIT));
    }

    private static void processCommand(CineReviewsInterface cr, CMD command, Scanner in)   {
        switch (command) {
            case REGISTER -> registerUser(cr, in);
            case USERS -> listUsers(cr);
            case MOVIE -> registerShow(ShowType.Movie, cr, in);
            case SERIES -> registerShow(ShowType.Series, cr, in);
            case SHOWS -> listShows(cr);
            case ARTIST -> registerBio(cr, in);
            case CREDITS -> listCredits(cr, in);
            case REVIEW -> registerReview(cr, in);
            case REVIEWS -> listReviews(cr, in);
            case GENRE -> listWithGenre(cr,in);
            case RELEASED -> listReleasedIn(cr,in);
            case AVOIDERS -> listAvoiders(cr);
            case FRIENDS -> listFriends(cr);
            case HELP -> listCommands();
            case EXIT -> System.out.print(MSG.EXIT);
            case UNKNOWN -> System.out.print(ERROR.UNKNOWN_COMMAND);
        }
    }

    /**
     * REGISTER command
     * Registers a user in the system
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void registerUser(CineReviewsInterface cr, Scanner in) {

        //Inputs
        String userType = in.next().trim();
        String username = in.next().trim();
        String password = in.nextLine().trim(); //might be null

        try {
            cr.addUser(userType,username,password);
            System.out.printf(MSG.USER_REGISTERED.msg,username,userType);
        }

        //Exceptions handling
        catch (InvalidUserTypeException e) {System.out.print(ERROR.UNKNOWN_USER_TYPE);}
        catch (UserAlreadyExistsException e) {System.out.printf(ERROR.USER_ALREADY_EXISTS.msg,username);}
    }

    /**
     * USERS command
     * Lists all registered users
     * @param cr CineReviews instance
     */
    private static void listUsers(CineReviewsInterface cr) {

        try {
            Iterator<User> it = cr.listUsers();
            System.out.print(MSG.LIST_USERS);
            while (it.hasNext()) {
                User next = it.next();
                if (next instanceof SuperUser) {System.out.printf(MSG.LIST_USERS_ADMIN.msg,next.getName(),next.getCounter());}
                else if (next instanceof Reviewer) {System.out.printf(MSG.LIST_USERS_REVIEWER.msg,next.getName(),next.getCounter());}
            }

          //Exceptions handling
        } catch (EmptyUsersException e) {System.out.print(ERROR.NO_USERS);}
    }

    /**
     * MOVIE, SERIES commands
     * Registers a new show
     * @param type show type (series, movie)
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void registerShow(ShowType type, CineReviewsInterface cr, Scanner in) {

        //Inputs
        String adminUsername = in.next().trim();
        String password = in.nextLine().trim();
        String title = in.nextLine().trim();
        String showrunner = in.nextLine().trim();
        int duration_noSeasons = in.nextInt(); in.nextLine(); //duration (movie) / number of seasons (series) are both ints
        String ageCertification = in.nextLine().trim();
        int yearRelease = in.nextInt(); in.nextLine();

        //Input arrays
        List<String> genres = getListFromInput(in);
        List<String> cast = getListFromInput(in);

        try {
            //Sends input to System to add show
            int newArtists = cr.countArtists(); //get artist count before and after adding the show to know how many new artists were added
            cr.addShow(type, adminUsername, password, title, showrunner, duration_noSeasons, ageCertification, yearRelease, genres, cast);
            newArtists = cr.countArtists()-newArtists;

            //Outputs
            switch (type) {
                case Movie -> System.out.printf(MSG.MOVIE_REGISTERED.msg, title, yearRelease, newArtists);
                case Series -> System.out.printf(MSG.SERIES_REGISTERED.msg, title, yearRelease, newArtists);
            }

        //Exceptions handling
        } catch (AdminNotFoundException e) {
            System.out.printf(ERROR.ADMIN_NOT_FOUND.msg, adminUsername);
        } catch (WrongAdminPasswordException e) {
            System.out.printf(ERROR.INVALID_PASSWORD.msg);
        } catch (ShowAlreadyExistsException e) {
            System.out.printf(ERROR.SHOW_ALREADY_EXISTS.msg, title);
        }

    }

    /**
     * Auxilliary MOVIE / SERIES method
     * Gets multiple inputs and stores them as a list
     * @param in scanner for user input
     * @return a list with user inputs
     */
    private static List<String> getListFromInput(Scanner in) {

        //Input
        int size = in.nextInt(); in.nextLine();

        List<String> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            String elem = in.nextLine().trim();
            list.add(elem);
        }

        return list;
    }

    /**
     * SHOWS command
     * Lists all shows
     * @param cr CineReviews instance
     */
    private static void listShows(CineReviewsInterface cr) {

        try {
            Iterator<Show> it = cr.listShows();
            System.out.print(MSG.LIST_SHOWS);
            while (it.hasNext()) {
                Show s = it.next();

                //print show info
                System.out.printf(MSG.LIST_SHOWS_EACH.msg, s.getTitle(), s.getShowrunner().getName(),
                        s.getLength(), s.getAgeCert(), s.getReleaseDate(), s.getGenres().get(0));

                //print up to 3 artists
                List<Artist> cast = s.getTopCast();
                if (cast.size()>3) printArtists(cast.subList(0,3),"; ");
                else printArtists(cast,"; ");
            }

          //Exception handling
        } catch (EmptyShowsException e) {System.out.print(ERROR.NO_SHOWS);}
    }

    /**
     * Auxiliary SHOWS, AVOIDERS method
     * Prints an array of artists with a customizable separator between each
     * @param set the set with artist objects
     * @param separator the separator that is printed between each artist
     */
    private static void printArtists(Collection<Artist> set, String separator) {

        Iterator<Artist> it = set.iterator();

        while (it.hasNext()) {
            System.out.print(it.next().getName());
            if (it.hasNext()) System.out.print(separator);
        }

        System.out.println();
}

    /**
     * ARTIST command
     * Adds bio information about an artist
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void registerBio(CineReviewsInterface cr, Scanner in) {

        //Inputs
        String name = in.nextLine().trim();
        String date = in.nextLine().trim();
        String place = in.nextLine().trim();

        try {
            cr.addBio(name, date, place);
            System.out.printf(MSG.ARTIST_REGISTERED.msg, name);
        }

        //Exceptions handling
        catch (ArtistAlreadyExistsException e) { //not an error, and outputs a different success message
            System.out.printf(MSG.ARTIST_UPDATED.msg,name);
        }
        catch (BioAlreadyExistsException e) {
            System.out.printf(ERROR.BIO_ALREADY_EXISTS.msg,name);
        }
    }

    /**
     * CREDITS command
     * Lists the bio and credits of an artist
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void listCredits(CineReviewsInterface cr, Scanner in) {

        //Input
        String artistName = in.nextLine().trim();

        try {
            //lists credits
            Iterator<String> it = cr.listCredits(artistName);
            while (it.hasNext()) {
                System.out.println(it.next());
            }
        }

        //Exceptions handling
        catch (ArtistNotFoundException e) {
            System.out.printf(ERROR.ARTIST_NOT_FOUND.msg,artistName);
        }
    }

    /**
     * REVIEW command
     * Adds a review to a show
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void registerReview(CineReviewsInterface cr, Scanner in) {

        //Inputs
        String user = in.next().trim();
        String title = in.nextLine().trim();
        String comment = in.nextLine().trim();
        String classification = in.nextLine().trim();

        try {
            int count = cr.addReview(user,title,comment,classification);
            System.out.printf(MSG.REVIEW_REGISTERED.msg,title,count);
        }

        //Exceptions handling
        catch (UserNotFoundException e) {System.out.printf(ERROR.USER_NOT_FOUND.msg,user);}
        catch (UserIsAdminException e) {System.out.printf(ERROR.USER_NOT_REVIEWER.msg,user);}
        catch (ShowNotFoundException e) {System.out.printf(ERROR.SHOW_NOT_FOUND.msg,title);}
        catch (ReviewAlreadyExistsException e) {System.out.printf(ERROR.SHOW_ALREADY_REVIEWED.msg,user,title);}
    }

    /**
     * REVIEWS command
     * Lists the reviews of a show
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void listReviews(CineReviewsInterface cr, Scanner in) {

        //Input
        String title = in.nextLine().trim();

        try {
            double score = cr.getShowScore(title);
            Iterator<Review> it = cr.listReviews(title); //might throw EmptyReviewsException, ShowNotFoundException
            System.out.printf(MSG.LIST_REVIEWS.msg,title,score);
            while (it.hasNext()) {
                Review r = it.next();
                System.out.printf(MSG.LIST_REVIEWS_EACH.msg,r.getReviewer().getName(), r.getReviewer().getType(), r.getComment(), r.getClassification());
            }
        }

        //Exceptions handling
        catch (EmptyReviewsException e) {System.out.printf(ERROR.NO_REVIEWS.msg,title);}
        catch (ShowNotFoundException e) {System.out.printf(ERROR.SHOW_NOT_FOUND.msg,title);}
    }

    /**
     * GENRE command
     * Lists shows of given genres
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void listWithGenre(CineReviewsInterface cr, Scanner in) {

        List<String> genreArray = getListFromInput(in);

        try {
            Iterator<Show> it = cr.listShowsByGenre(genreArray);
            System.out.printf(MSG.LIST_GENRES.msg);
            printShows(it);
        }

        //Exceptions handling
        catch (EmptyShowsException | ShowCriteriaNotFoundException e) {
            System.out.printf(ERROR.SHOW_CRITERIA_NOT_FOUND.msg);
        }
    }

    /**
     * Auxilliary GENRE, RELEASED method
     * Prints information about shows
     * @param it iterator of shows to be printed
     */
    private static void printShows(Iterator<Show> it) {

        while (it.hasNext()) {
            Show s = it.next();
            System.out.printf(MSG.LIST_SEARCH_RESULT.msg, s.getShowType(), s.getTitle(), s.getShowrunner().getName(), s.getReleaseDate(), s.getScore());
        }
    }


    /**
     * RELEASED command
     * Lists shows released in a given year
     * @param cr CineReviews instance
     * @param in scanner for user input
     */
    private static void listReleasedIn(CineReviewsInterface cr, Scanner in) {

        //Input
        int releasedYear = in.nextInt(); in.nextLine();

        try {
            Iterator <Show> it = cr.listShowsByYear(releasedYear);
            System.out.printf(MSG.LIST_RELEASED.msg, releasedYear);
            printShows(it);
        }

        //Exceptions handling
        catch (EmptyShowsException | ShowCriteriaNotFoundException e) {
            System.out.printf(ERROR.SHOW_CRITERIA_NOT_FOUND.msg);}
    }

    /**
     * AVOIDERS command
     * Lists artists that have no common projects
     * @param cr CineReviews instance
     */
    private static void listAvoiders(CineReviewsInterface cr) {

        try {
            SortedSet<SortedSet<Artist>> avoiders = cr.getMaxAvoiders();
            int size = avoiders.first().size(); //gets max size of avoiders arrays
            Iterator<SortedSet<Artist>> avoidersIt = avoiders.iterator();
            System.out.printf(MSG.LIST_AVOIDERS.msg,size);
            while (avoidersIt.hasNext()) {
                printArtists(avoidersIt.next(),", ");
            }
        }

        //Exceptions handling
        catch (EmptyArtistsException e) {System.out.print(ERROR.NO_ARTISTS);}
        catch (EmptyAvoidersException e) {System.out.print(ERROR.NO_AVOIDERS);}
    }

    /**
     * FRIENDS command
     * Lists artists that have more projects together
     * @param cr CineReviews instance
     */
    private static void listFriends(CineReviewsInterface cr) {

        try {
            SortedMap<Pair, Integer> friends = cr.getMaxFriends();
            System.out.printf(MSG.LIST_FRIENDS.msg,friends.values().iterator().next()); //gets first value (max)
            Iterator<Pair> keyit = friends.keySet().iterator();
            while (keyit.hasNext()) {
                ArtistPair pair = (ArtistPair) keyit.next();
                System.out.printf(MSG.LIST_FRIENDS_EACH.msg,pair.getA().getName(),pair.getB().getName());
            }
        }

        //Exceptions handling
        catch (EmptyArtistsException e) {System.out.print(ERROR.NO_ARTISTS);}
        catch (EmptyFriendsException e) {System.out.print(ERROR.NO_FRIENDS);}
    }

    /**
     * HELP command
     * Shows the available commands
     */
    private static void listCommands() {

        for (CMD c : CMD.values()) {
            if (c!=CMD.UNKNOWN) System.out.println(c); //Ignores the null/UNKNOWN command;
        }
    }

}