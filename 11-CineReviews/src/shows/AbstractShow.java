package shows;

import java.util.*;
import artists.*;
import exceptions.EmptyReviewsException;
import exceptions.ReviewAlreadyExistsException;
import reviews.*;
import users.*;

/**
 * AbstractShow - common variables and methods between different show types
 */
public abstract class AbstractShow implements Show {
    //VARIABLES
    private final String title; //Show's title
    private final Artist showrunner; //Show's director || creator
    private final int length; //Show's duration || number of seasons
    private final String ageCert; //Show's age certification
    private final int releaseDate; //Show's release date (year)
    private final List<String> genres; //List of show's genre(s)
    private final List<Artist> topCast; //List of show's top cast
    private final Map<Reviewer,Review> reviews; //Map of show's reviews

    //CONSTRUCTOR

    /**
     * Show is registered and associated with the following infomation:
     * @param title show's title
     * @param showrunner show's director || creator
     * @param length show's duration || number of seasons
     * @param ageCert show's age certification
     * @param releaseDate show's release date
     * @param genres show's genre(s)
     * @param topCast show's top cast
     */
    AbstractShow(String title, Artist showrunner, int length, String ageCert, int releaseDate, List<String> genres, List<Artist> topCast)   {
        this.title = title;
        this.showrunner=showrunner;
        this.length=length;
        this.ageCert=ageCert;
        this.releaseDate=releaseDate;
        this.genres=genres;
        this.topCast=topCast;
        this.reviews = new HashMap<>();
        giveCredits(topCast);
        giveCredits(showrunner);
    }

    private void giveCredits(List<Artist> artists) {
        Iterator<Artist> it = artists.iterator();
        while (it.hasNext()) {
            giveCredits(it.next());
        }
    }

    private void giveCredits(Artist artist) {
        artist.addCredits(this);
    }

    //METHODS

    //GET
    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Artist getShowrunner() {
        return showrunner;
    }

    @Override
    public int getLength() {return length;}

    @Override
    public String getAgeCert() {
        return ageCert;
    }

    @Override
    public int getReleaseDate() {
        return releaseDate;
    }

    @Override
    public List<String> getGenres() {
        return genres;
    }

    @Override
    public List<Artist> getTopCast() {
        return topCast;
    }

    @Override
    public Set<Pair> getCollaborators() {
        SortedSet<Artist> collaborators = new TreeSet<>(topCast);
        collaborators.add(showrunner);

        Set<Pair> collaboratorPairs = new HashSet<>(); //power set of 2 elements of collaborators set
        for (Artist a : collaborators) {
            for (Artist b : collaborators.tailSet(a)) {
                if (b.equals(a)) continue; //skip a;
                Pair pair = new ArtistPair(a,b);
                collaboratorPairs.add(pair);
            }
        }

        return collaboratorPairs;
    }

    @Override
    public double getScore() {
        int sumScore = 0;
        int sumWeight = 0;
        try {
            Iterator<Review> it = listReviews();
            while (it.hasNext()) {
                Review next = it.next();
                int weight = next.getWeight();
                sumWeight += weight;
                sumScore += next.getScore() * weight;
            }
            return (double) sumScore/sumWeight;
        }
        catch (EmptyReviewsException e) {return 0;}
    }
    @Override
    public int getCountReviews() {return reviews.values().size();}

    //LOGIC
    @Override
    public void addReview(Reviewer user, Review review) throws ReviewAlreadyExistsException {
        if (reviews.containsKey(user)) throw new ReviewAlreadyExistsException();
        reviews.put(user,review);
    }

    //LIST
    @Override
    public Iterator<Review> listReviews() throws EmptyReviewsException {
        if (reviews.isEmpty()) throw new EmptyReviewsException();
        SortedSet<Review> reviewsList = new TreeSet<>(new ReviewComparator());
        reviewsList.addAll(reviews.values());
        return reviewsList.iterator();
    }



}
