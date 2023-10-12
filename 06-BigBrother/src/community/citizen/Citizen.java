package community.citizen;
import community.*;
import dataStructures.*;

/**
 * Citizen interface
 * Methods that manage citizen objects
 * Citizens start && share gossips, hear && forget gossips, and go in and out of groups && landmarks
 */
public interface Citizen {

    //Getters
    /**
     * Returns the citizen's name
     * @return citizen's name
     */
    String getName();

    /**
     * Returns what landmark the citizen is in
     * pre !isHome()
     * @return what landmark the citizen is in
     */
    Landmark getLocation();

    /**
     * Returns the name of the landmark the citizen is in
     * @return the name of the landmark the citizen is in
     */
    String getLocationName();

    /**
     * Returns how many gossips the citizen can share
     * @return loquacity - how many gossips the citizen can share
     */
    int getLoquacity();

    /**
     * Returns the number of gossips the citizen knows of
     * @return number of gossips the citizen knows of
     */
    int countRememberedGossips();

    /**
     * Checks if the citizen is home
     * @return true if the citizen is home
     */
    boolean isHome();

    /**
     * Checks if the citizen is home OR alone on a group
     * @return true if the citizen is home OR alone on a group
     */
    boolean isAlone();

    /**
     * Checks if citizen is aware of a gossip && willing to share it
     * @return true if citizen is aware of a gossip && willing to share it
     */
    boolean hasGossipsToShare();

    /**
     * Lists the gossips a citizen remembers
     * @return iterator with the gossips a citizen remembers
     */
    Iterator<Gossip> listRememberedGossips();

    /**
     * Lists the gossips a citizen is aware of in order starting with the next gossip to share
     * Pays no mind to each subclass' sharing restrictions
     * @return iterator with the gossips
     */
    Iterator<Gossip> listGossipsSharingOrder();

    /**
     * Makes a citizen go home
     */
    void goHome();

    /**
     * Moves a citizen to a landmark
     * @param landmark the landmark the citizen is moving to
     */
    void goTo(Landmark landmark);

    /**
     * Manages gossip's index inside listGossipsSharingOrder()
     * @param g gossip being managed
     */
    void updateGossipIndex(Gossip g);

    /**
     * Makes a citizen aware of a gossip
     * @param gossip the gossip to be made aware of
     */
    void hearGossip(Gossip gossip);
}