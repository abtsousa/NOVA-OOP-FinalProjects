import artists.*;
import exceptions.*;
import reviews.*;
import users.*;
import shows.*;

import java.util.*;

/**
 * CineReviewsPlatform Interface:
 * Methods that manage the app (users, artists, shows, reviews)
 * Intermediates between Main and other classes
 * Manages, lists and verifies information of other classes
 */
interface CineReviewsInterface {

    /**
     * Adds a user to the platform (REGISTER command)
     * @param type type of user to add
     * @param username user's username
     * @param password user's password (null if not admin)
     * @throws InvalidUserTypeException if user type is not defined in the system
     * @throws UserAlreadyExistsException if there's already a user in the system with the same name
     */
    void addUser(String type, String username, String password) throws InvalidUserTypeException, UserAlreadyExistsException;

    /**
     * Lists all registered users (USERS command)
     * @return iterator of all registered users
     * @throws EmptyUsersException if there are no registered users
     */
    Iterator<User> listUsers() throws EmptyUsersException;

    /**
     * Uploads a new show (MOVIE && SERIES commands)
     * @param type type of show
     * @param adminUsername admin's username who is uploading the show
     * @param password admin's password
     * @param title show's title
     * @param showrunner show's director || creator
     * @param duration_noSeasons show's duration || number of Seasons
     * @param ageCert show's age certification
     * @param releaseDate show's release year
     * @param genres show's genre(s)
     * @param tmpCast show's top cast
     * @throws AdminNotFoundException if the given username is not a valid Admin
     * @throws WrongAdminPasswordException if the given password is wrong
     * @throws ShowAlreadyExistsException if a show with the same title already exists in the system
     */
    void addShow(ShowType type, String adminUsername, String password, String title, String showrunner, int duration_noSeasons, String ageCert, int releaseDate, List<String> genres, List<String> tmpCast) throws AdminNotFoundException, WrongAdminPasswordException, ShowAlreadyExistsException;

    /**
     * Auxilliary MOVIE, SERIES method
     * Counts how many artists are in the system
     * @return number of artists in the system
     */
    int countArtists();

    /**
     * Lists all shows (SHOWS command)
     * @return an iterator of all shows by alphabetical order of their title
     * @throws EmptyShowsException if there are no shows uploaded to the platform
     */
    Iterator<Show> listShows() throws EmptyShowsException;

    /**
     * Adds bio information about an artist (ARTIST command)
     * @param name artist's name
     * @param date artist's date of birth
     * @param placeOfBirth input artist's place of birth
     * @throws BioAlreadyExistsException if the artist already has a bio
     * @throws ArtistAlreadyExistsException if the artist is already registered in the platform
     */
    void addBio(String name, String date, String placeOfBirth) throws BioAlreadyExistsException, ArtistAlreadyExistsException;

    /**
     * Lists the bio and credits of an artist (CREDITS command)
     * @param artistName artist's name who's being listed
     * @return an iterator of their bio, if available, and all their credits sorted by release year
     * (more recent first), and then by title
     * @throws ArtistNotFoundException if the artist with that name does not exist
     */
    Iterator<String> listCredits(String artistName) throws ArtistNotFoundException;

    /**
     * Adds a review to a show (REVIEW command)
     * @param username user's name posting the review
     * @param title show's title being reviewed
     * @param comment review's comment
     * @param classification review's classification
     * @return current number of reviews for that show
     * @throws UserNotFoundException if the username does not exist
     * @throws UserIsAdminException if the username is associated to an admin
     * @throws ShowNotFoundException if the title is not associated with any show
     * @throws ReviewAlreadyExistsException if the show with title was already reviewed by the username
     */
    int addReview(String username, String title, String comment, String classification) throws UserNotFoundException, UserIsAdminException, ShowNotFoundException, ReviewAlreadyExistsException;

    /**
     * Lists the reviews of a show (REVIEWS command)
     * @param showTitle show's title
     * @return an iterator of the reviews showing first the critics and then the audience members,
     * sorted from the highest to the lowest score and then alphabetically
     * by username
     * @throws ShowNotFoundException if the title is not associated with any show
     * @throws EmptyReviewsException if there are no reviews posted
     */
    Iterator<Review> listReviews(String showTitle) throws ShowNotFoundException, EmptyReviewsException;

    /**
     * Auxilliary REVIEWS method
     * Returns the show's score
     * @param title show's title
     * @return the show's score
     * @throws ShowNotFoundException if the title is not associated with any show
     */
    double getShowScore(String title) throws ShowNotFoundException;

    /**
     * Lists shows of given genres (GENRE command)
     * @param genres selected genre(s)
     * @return iterator of the shows that cover all the genres ordered by score (average of reviews),
     * then by release date, and lastly by title
     * @throws EmptyShowsException if there are no shows uploaded to the platform
     * @throws ShowCriteriaNotFoundException if no show was found with all the genres received as input
     */
    Iterator<Show> listShowsByGenre(List<String> genres) throws EmptyShowsException, ShowCriteriaNotFoundException;

    /**
     * Lists shows released on a given year (RELEASED command)
     * @param year selected release year
     * @return iterator of the shows with the same release date ordered by average score, and
     * then by title
     * @throws EmptyShowsException if there are no shows uploaded to the platform
     * @throws ShowCriteriaNotFoundException if no show was found with that release date
     */
    Iterator<Show> listShowsByYear(int year) throws EmptyShowsException, ShowCriteriaNotFoundException;

    /**
     * Artists that have no common projects (AVOIDERS command)
     *
     * @return list the largest group of artists that have never participated in a common movie or series
     * @throws EmptyArtistsException  if no artist exists in the application
     * @throws EmptyAvoidersException if all artists have common projects || there's only one artist registered
     */
    SortedSet<SortedSet<Artist>> getMaxAvoiders() throws EmptyArtistsException, EmptyAvoidersException;

    /**
     * Artists that have more projects together (FRIENDS command)
     * @return lists the pairs of artists that have more shows together
     * @throws EmptyArtistsException if no artist exists in the application
     * @throws EmptyFriendsException if no collaborations exist between any artists
     */
    SortedMap<Pair, Integer> getMaxFriends() throws EmptyArtistsException, EmptyFriendsException;


}
