package community;
import community.citizen.Citizen;
import dataStructures.Array;

/**
 *  Gossip Interface
 *  Methods that manage gossip objects' information
 *  Gets gossips' information and may compare them
 */
public interface Gossip {

    /**
     * Returns the gossip's creator
     * @return gossip's creator
     */
    Citizen getCreator();

    /**
     * Returns the gossip's content
     * @return gossip's content
     */
    String getDescription();

    /**
     * Returns the number of times the gossip was shared
     * @return number of times the gossip was shared
     */
    int howManyShares();

    /**
     * Returns how many citizens remember this gossip
     * @return how many citizens remember this gossip
     */
    int countRememberedBy();

    /**
     * Checks if the citizen is targetted by this gossip
     * @param citizen citizen who might or might not be targetted by this gossip
     * @return true if the citizens is targetted by this gossip
     */
    boolean hasTarget(Citizen citizen);

    /**
     * Checks if no citizens remember this gossip
     * @return true if no citizens remember this gossip
     */
    boolean isForgotten();

    /**
     * Adds another share to the gossip's hotness
     * @param citizen person's name sharing the gossip
     */
    void addToShareChain(Citizen citizen);

    /**
     * Registers the citizen being aware of this gossip
     * @param citizen citizen who now knows of this gossip
     */
    void addToRememberedBy(Citizen citizen);

    /**
     * Registers the citizen forgetting this gossip
     * pre knownBy(citizen)
     * @param citizen citizen forgetting this gossip
     */
    void removeFromRememberedBy(Citizen citizen);

    /**
     * Checks if two gossips have the same creator && targets && description
     * @param creator gossip's creator being compared
     * @param targets targeted citizens being compared
     * @param description gossip's description being compared
     * @return true if both gossips have the same creator && targets && description
     */
    boolean compareTo(Citizen creator, Array<Citizen> targets, String description);
}
