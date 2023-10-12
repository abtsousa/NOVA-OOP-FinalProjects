import java.util.*;

import artists.*;
import exceptions.*;
import reviews.*;
import shows.*;
import users.*;

/**
 * CineReviews - System Management
 */
public class CineReviews implements CineReviewsInterface {
    //VARIABLES
    private final SortedMap<String,User> users; //SortedMap of app's registered users
    private final SortedMap<String,Show> shows; //SortedMap of app's registered shows
    private final Map<String,Artist> artists; //Map of app's registered artists
    private final Map<String, List<Show>> showsByGenre; //Map of shows by Genre
    private final Map<Integer, List<Show>> showsByReleaseYear; //Map of shows by Release Date
    private final SortedMap<Pair, Integer> friends; //SortedMap of pairs of artists who worked together

    //CONSTRUCTOR
    /**
     * System class - the CineReviews platform collects reviews from
     * movie critics and audiences to determine a movie or series' overall score
     */
    CineReviews() {
        users = new TreeMap<>();
        shows = new TreeMap<>();
        artists = new HashMap<>();
        showsByGenre = new HashMap<>();
        showsByReleaseYear = new HashMap<>();
        friends = new TreeMap<>();
    }

    //METHODS

    /**
     * Adds a user to the platform (REGISTER command)
     * @param type type of user to add
     * @param username user's username
     * @param password user's password (null if not admin)
     * @throws InvalidUserTypeException if user type is not defined in the system
     * @throws UserAlreadyExistsException if there's already a user in the system with the same name
     */
    @Override
    public void addUser(String type, String username, String password) throws InvalidUserTypeException, UserAlreadyExistsException {

        //check if admin
        UserType userType;
        try {userType = UserType.valueOf(type);}
        catch (IllegalArgumentException e) {throw new InvalidUserTypeException();}
        if (userType.equals(UserType.admin)) addAdmin(userType, username, password);
        else addReviewer(userType, username);
    }

    /**
     * Auxilliary REGISTER method
     * Adds a reviewer to the system
     * @param type type of reviewer to add
     * @param name user's name
     * @throws InvalidUserTypeException if given user type is not a valid Reviewer type
     * @throws UserAlreadyExistsException if there's already a user in the system with the same name
     */
    private void addReviewer(UserType type, String name) throws InvalidUserTypeException, UserAlreadyExistsException {
        User user;
        switch (type) {
            case audience -> user = new AudienceReviewer(name);
            case critic -> user = new CriticReviewer(name);
            //modular switch, additional user types could be added easily
            default -> throw new InvalidUserTypeException();
        }
        addToUsers(name,user);
    }

    /**
     * Auxilliary REGISTER method
     * Adds a superuser/admin to the system
     * @param type type of admin to add
     * @param name user's name
     * @param password user's password
     * @throws InvalidUserTypeException if given user type is not a valid SuperUser type
     * @throws UserAlreadyExistsException if there's already a user in the system with the same name
     */
    private void addAdmin(UserType type, String name, String password) throws InvalidUserTypeException, UserAlreadyExistsException {
        User user;
        switch (type) {
            case admin -> user = new Admin(name, password);
            //modular switch, additional user types could be added easily
            default -> throw new InvalidUserTypeException();
        }
        addToUsers(name,user);
    }

    /**
     * Auxilliary REGISTER method
     * Adds user to system
     * @param name user name
     * @param user user object
     * @throws UserAlreadyExistsException if there's already a user in the system with the same name
     */
    private void addToUsers(String name, User user) throws UserAlreadyExistsException {
        if (users.containsKey(name)) throw new UserAlreadyExistsException();
        users.put(name, user);
    }

    /**
     * Lists all registered users (USERS command)
     * @return iterator of all registered users
     * @throws EmptyUsersException if there are no registered users
     */
    @Override
    public Iterator<User> listUsers() throws EmptyUsersException {
        if (users.isEmpty()) throw new EmptyUsersException();
        return users.values().iterator();
    }

    /**
     * Uploads a new show (MOVIE && SERIES commands)
     * @param type type of show
     * @param adminUsername admin's username who is uploading the show
     * @param password admin's password
     * @param title show's title
     * @param showrunnerName show's director || creator
     * @param duration_noSeasons show's duration || number of Seasons
     * @param ageCert show's age certification
     * @param releaseDate show's release year
     * @param genres show's genre(s)
     * @param castNames show's top cast
     * @throws AdminNotFoundException if the given username is not a valid Admin
     * @throws WrongAdminPasswordException if the given password is wrong
     * @throws ShowAlreadyExistsException if a show with the same title already exists in the system
     */
    @Override
    public void addShow (ShowType type, String adminUsername, String password, String title, String showrunnerName, int duration_noSeasons, String ageCert, int releaseDate, List<String> genres, List<String> castNames) throws AdminNotFoundException, WrongAdminPasswordException, ShowAlreadyExistsException {

        //Check exceptions
        SuperUser admin;
        try {admin = (SuperUser) users.get(adminUsername);}
        catch (ClassCastException e) {throw new AdminNotFoundException();} //user not admin
        if (admin==null) throw new AdminNotFoundException(); //user not found
        if (!admin.passwordCheck(password)) {throw new WrongAdminPasswordException();} //wrong password
        if (shows.containsKey(title)) {throw new ShowAlreadyExistsException();} //show already exists

        //Add cast and showrunner to artists
        List<Artist> cast = getArtists(castNames);
        Artist showrunner;
        try {showrunner = getArtist(showrunnerName);} //Add showrunner to artists
        catch (ArtistNotFoundException e) {showrunner = createArtist(showrunnerName);}

        //Create show
        Show show = createShow(type, title, showrunner, duration_noSeasons, ageCert, releaseDate, genres, cast);
        shows.put(title, show); //Add to shows map
        admin.incrementCounter(); //increments shows created by admin
        addToMap(showsByReleaseYear,releaseDate,show);
        for (String genre : show.getGenres()) {
            addToMap(showsByGenre, genre, show);
        }
        updateFriends(show); //updates friends map with the collaborations
    }

    /**
     * Auxilliary MOVIE, SERIES method
     * Counts how many artists are in the system
     * @return number of artists in the system
     */
    @Override
    public int countArtists() {
        return artists.size();
    }

    /**
     * Auxilliary MOVIE, SERIES method
     * Converts a list of artists names into a list of their respective objects
     * Also creates new Artist objects if they didn't exist before
     * @param ArtistsNames the list with all the artists' names to be added
     * @return list of artist objects with all the artists (cast)
     */
    private List<Artist> getArtists(List<String> ArtistsNames)  {
        List<Artist> cast = new ArrayList<>();

        for (String name : ArtistsNames) {
            Artist artist;
            try {artist = getArtist(name);}
            catch (ArtistNotFoundException a) {artist = createArtist(name);}
            cast.add(artist);
        }
        return cast;
    }

    /**
     * Auxilliary MOVIE, SERIES method
     * Creates an Artist object and adds it into the system
     * @param name the artist's name
     * @return Artist object
     */
    private Artist createArtist(String name) {
        Artist artist = new GenericArtist(name);
        artists.put(name, artist);
        return artist;
    }

    /**
     * Auxilliary MOVIE, SERIES method
     * Creates an Artist object and adds it into the system (with bio)
     * @param name the artist's name
     * @param date the artist's date of birth
     * @param placeOfBirth the artist's place of birth
     */
    private void createArtist(String name, String date, String placeOfBirth) {
        Artist artist = new GenericArtist(name, date, placeOfBirth);
        artists.put(name, artist);
    }

    /**
     * Auxiliary MOVIE, SERIES method
     * Gets an Artist object from their name
     * @param name the artist's name
     * @return the Artist object with that name
     * @throws ArtistNotFoundException if there's no artist with that name registered in the system
     */
    private Artist getArtist(String name) throws ArtistNotFoundException {
        Artist artist = artists.get(name);
        if (artist==null) throw new ArtistNotFoundException();
        return artist;
    }

    /**
     * Auxiliary MOVIE, SERIES method
     * Creates a new Show object
     * @param type type of show
     * @param title show's title
     * @param showrunner show's director || creator
     * @param duration_noSeasons show's duration || number of Seasons
     * @param ageCert show's age certification
     * @param releaseDate show's release year
     * @param genres show's genre(s)
     * @param cast show's top cast
     * @return a new Show object
     */
    private Show createShow(ShowType type, String title, Artist showrunner, int duration_noSeasons, String ageCert, int releaseDate, List<String> genres, List<Artist> cast) {
        Show show = null;
        switch (type) {
            case Movie -> show = new MovieClass(title, showrunner, duration_noSeasons, ageCert, releaseDate, genres, cast);
            case Series -> show = new SeriesClass(title, showrunner, duration_noSeasons, ageCert, releaseDate, genres, cast);
        }
        return show;
    }

    /**
     * Auxilliary MOVIE, SERIES method
     * Generically, it adds any object to an existing list inside a map
     * (and creates a new list if it didn't exist before).
     * In practice, it is used to add each new show to their respective genres / releaseYear lists
     * in the showsByGenre / showsByReleaseYear maps.
     * @param map the map where we want to add the value (showsByGenre or showsByReleaseYear)
     * @param key the respective key (genre or release year)
     * @param value the value to be added (show)
     * @param <K> key
     * @param <V> value
     */
    private <K, V> void addToMap(Map<K,List<V>> map, K key, V value) {
        List<V> list;
        if (map.containsKey(key)) {list = map.get(key);}
        else {list = new ArrayList<>();}
        list.add(value);
        map.put(key,list);
    }

    /**
     * Lists all shows (SHOWS command)
     * @return an iterator of all shows by alphabetical order of their title
     * @throws EmptyShowsException if there are no shows uploaded to the platform
     */
    @Override
    public Iterator<Show> listShows() throws EmptyShowsException {
        if (shows.isEmpty()) throw new EmptyShowsException();
        return shows.values().iterator();
    }

    /**
     * Adds bio information about an artist (ARTIST command)
     * @param name artist's name
     * @param date artist's date of birth
     * @param placeOfBirth artist's place of birth
     * @throws BioAlreadyExistsException if the artist already has a bio
     * @throws ArtistAlreadyExistsException if the artist is already registered in the platform
     */
    @Override
    public void addBio(String name, String date, String placeOfBirth) throws BioAlreadyExistsException, ArtistAlreadyExistsException {
        Artist artist;
        try {
            artist = getArtist(name);
            artist.addBio(date, placeOfBirth); //might throw BioAlreadyExistsException (error)
            throw new ArtistAlreadyExistsException(); //signals to main that artist already existed (not an error)
        }
        catch (ArtistNotFoundException e) {
            createArtist(name, date, placeOfBirth);
        }
    }

    /**
     * Lists the bio and credits of an artist (CREDITS command)
     * @param artistName artist's name who's being listed
     * @return an iterator of their bio, if available, and all their credits sorted by release year
     * (more recent first), and then by title
     * @throws ArtistNotFoundException if the artist with that name does not exist
     */
    @Override
    public Iterator<String> listCredits(String artistName) throws ArtistNotFoundException {
        List<String> list = new ArrayList<>();
        Artist artist = getArtist(artistName); //might throw ArtistNotFoundException

        //Get artist bio if available
        try {list.addAll(getArtistBio(artist));}
        catch (EmptyBioException ignored) {} //ignore bio if empty

        //Get artist credits
        Iterator<Show> it = artist.getCredits();
        while (it.hasNext())    {
            Show s = it.next();
            list.add(s.getTitle()+"; "+s.getReleaseDate()+"; "+artist.getRole(s)+" ["+s.getShowType().toLower()+"]");
        }
        return list.iterator();
    }

    /**
     * Auxilliary CREDITS method
     * Gets an artist's bio if it exists
     * @param artist the artist
     * @return list with the artist's bio
     * @throws EmptyBioException if the artist has no bio
     */
    private List<String> getArtistBio(Artist artist) throws EmptyBioException {
        List<String> list = new ArrayList<>();
        list.add(artist.getDateOfBirth());
        list.add(artist.getPlaceOfBirth());
        return list;
    }

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
    @Override
    public int addReview(String username, String title, String comment, String classification) throws UserNotFoundException, UserIsAdminException, ShowNotFoundException, ReviewAlreadyExistsException {
        User user = getUser(username); //might throw UserNotFoundException
        if (user instanceof SuperUser) throw new UserIsAdminException();
        Show show = getShow(title); //might throw ShowNotFoundException
        Review review = new GenericReview((Reviewer) user,comment,classification);
        show.addReview((Reviewer) user,review); //might throw ReviewAlreadyExistsException
        user.incrementCounter();
        return show.getCountReviews();
    }

    /**
     * Auxilliary REVIEW method
     * Gets a User object from their name
     * @param name the user's name
     * @return the User object with that name
     * @throws UserNotFoundException if no user with that name is registered in the system
     */
    private User getUser(String name) throws UserNotFoundException {
        if (!users.containsKey(name)) throw new UserNotFoundException();
        return users.get(name);
    }

    /**
     * Auxilliary REVIEW method
     * Gets a Show object from its title
     * @param name the title of the show
     * @return the Show object with that title
     * @throws ShowNotFoundException if no show with that title is registered in the system
     */
    private Show getShow(String name) throws ShowNotFoundException {
        if (!shows.containsKey(name)) throw new ShowNotFoundException();
        return shows.get(name);
    }

    /**
     * Lists the reviews of a show (REVIEWS command)
     * @param showTitle show's title
     * @return an iterator of the reviews showing first the critics and then the audience members,
     * sorted from the highest to the lowest score and then alphabetically
     * by username
     * @throws ShowNotFoundException if the title is not associated with any show
     * @throws EmptyReviewsException if there are no reviews posted
     */
    @Override
    public Iterator<Review> listReviews(String showTitle) throws ShowNotFoundException, EmptyReviewsException {
        Show show = getShow(showTitle); //might throw ShowNotFoundException
        return show.listReviews(); //might throw EmptyReviewsException
    }

    /**
     * Auxilliary REVIEWS method
     * Returns the show's score
     * @param title show's title
     * @return the show's score
     * @throws ShowNotFoundException if the title is not associated with any show
     */
    @Override
    public double getShowScore(String title) throws ShowNotFoundException {
        return getShow(title).getScore();
    }

    /**
     * Lists shows of given genres (GENRE command)
     * @param genres selected genre(s)
     * @return iterator of the shows that cover all the genres ordered by score (average of reviews),
     * then by release date, and lastly by title
     * @throws EmptyShowsException if there are no shows uploaded to the platform
     * @throws ShowCriteriaNotFoundException if no show was found with all the genres received as input
     */
    @Override
    public Iterator<Show> listShowsByGenre(List<String> genres) throws EmptyShowsException, ShowCriteriaNotFoundException {
        if (shows.isEmpty()) {throw new EmptyShowsException();}

        //create set with first genre
        SortedSet<Show> result = new TreeSet<>(new ShowComparator());
        result.addAll(showsByGenre.get(genres.get(0)));

        //intersect set with the other genres
        for (int i=1; i<genres.size(); i++) {
            result.retainAll(showsByGenre.get(genres.get(i)));
        }

        if (result.isEmpty()) throw new ShowCriteriaNotFoundException(); //no result
        return result.iterator(); //result was found
    }

    /**
     * Lists shows released on a given year (RELEASED command)
     * @param year selected release year
     * @return iterator of the shows with the same release date ordered by average score, and
     * then by title
     * @throws EmptyShowsException if there are no shows uploaded to the platform
     * @throws ShowCriteriaNotFoundException if no show was found with that release date
     */
    @Override
    public Iterator<Show> listShowsByYear(int year) throws EmptyShowsException, ShowCriteriaNotFoundException{
        if (shows.isEmpty())    {throw new EmptyShowsException();}
        if (!showsByReleaseYear.containsKey(year)) {throw new ShowCriteriaNotFoundException();}
        List<Show> list = showsByReleaseYear.get(year);
        list.sort(new ShowComparator()); //score is mutable so we must reorder
        return list.iterator();
    }

    /**
     * Artists that have no common projects (AVOIDERS command)
     * @return set of largest groups of artists that have never participated in a common movie or series
     * @throws EmptyArtistsException  if no artist exists in the application
     * @throws EmptyAvoidersException if all artists have common projects OR there's only one artist registered
     */
    @Override
    public SortedSet<SortedSet<Artist>> getMaxAvoiders() throws EmptyArtistsException, EmptyAvoidersException {
        if (artists.isEmpty()) throw new EmptyArtistsException();
        SortedSet<SortedSet<Artist>> avoiders = getAvoidersSet();

        int size = avoiders.first().size();
        if (size < 2) throw new EmptyAvoidersException(); //no point in listing sets with less than 2 elements

        //remove non-maximum elements
        Iterator<SortedSet<Artist>> avoidersIt = avoiders.iterator();
        while (avoidersIt.hasNext()) {
            if (avoidersIt.next().size()<size) avoidersIt.remove();
        }

        return avoiders;
    }

    /**
     * Auxilliary AVOIDERS method
     * Computes a set with all the possible avoiders sets (not just the largest)
     * @return a subset of the artists powerset with all the artist groups that never collaborated together (avoiders)
     */
    private SortedSet<SortedSet<Artist>> getAvoidersSet() {
        SortedSet<SortedSet<Artist>> set = getpowerSet(); //power set with all possible artists
        Iterator<Pair> collabIt = friends.keySet().iterator(); //gets pairs of artists that collaborated at least once

        while (collabIt.hasNext()) {  //removes collaborators from power set
            Iterator<SortedSet<Artist>> it = set.iterator();
            ArtistPair collabNext = (ArtistPair) collabIt.next();
            while (it.hasNext()) {
                SortedSet<Artist> next = it.next();
                if (collabNext.isIn(next)) it.remove();
            }
        }
        return set;
    }

    /**
     * Auxilliary AVOIDERS method
     * Computes a power set of the artists set
     * @return a set with all possible combinations (subsets) of the artists set
     */
    private SortedSet<SortedSet<Artist>> getpowerSet() {
        SortedSet<SortedSet<Artist>> subsets = new TreeSet<>(new ArtistSetComparator());
        subsets.add(new TreeSet<>());
        for (Artist artist : artists.values()) {
            SortedSet<SortedSet<Artist>> current = new TreeSet<>(new ArtistSetComparator());
            for (SortedSet<Artist> set : subsets) {
                SortedSet<Artist> newSet = new TreeSet<>(set);
                newSet.add(artist);
                current.add(newSet);
            }
            subsets.addAll(current);
        }
        return subsets;
    }

    /**
     * Artists that have more projects together (FRIENDS command)
     * @return lists the pairs of artists that have more shows together
     * @throws EmptyArtistsException if no artist exists in the application
     * @throws EmptyFriendsException if no collaborations exist between any artists
     */
    @Override
    public SortedMap<Pair, Integer> getMaxFriends() throws EmptyArtistsException, EmptyFriendsException {
        if (artists.isEmpty()) throw new EmptyArtistsException();
        if (friends.isEmpty()) throw new EmptyFriendsException();

        //find the highest collaborators
        int max = 0;
        for (int i : friends.values()) {if (i > max) max = i;}

        Iterator<Pair> it = friends.keySet().iterator();
        SortedMap<Pair, Integer> maxFriends = new TreeMap<>();
        while (it.hasNext()) {
            Pair next = it.next();
            if (friends.get(next) == max) maxFriends.put(next,max);
        }

        return maxFriends;
    }

    /**
     * Auxilliary FRIENDS method
     * When a show is added, this method is called to count each collaboration between two artists in that show
     * and updates the respective map (friends)
     * @param show the show that was added
     */
    private void updateFriends(Show show) {
        Iterator<Pair> it = show.getCollaborators().iterator(); //gets collaborator pairs
        while (it.hasNext()) { //iterates them and counts them
            Pair key = it.next();
            if (!friends.containsKey(key)) friends.put(key,1);
            else {
                int counter = friends.get(key);
                counter++;
                friends.put(key,counter);
            }
        }
    }

}
