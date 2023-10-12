package community;
import community.citizen.Citizen;
import dataStructures.*;

/**
 * A simple Gossip object.
 * A gossip has a creator, description, the people who are involved in the gossip, the sequence of
 * people who tried spreading the gossip and the people who remember the gossip.
 */
public class GossipClass implements Gossip {

    //VARIABLES
    private final Citizen creator; //Gossip's creator
    private final String description; //Gossip itself
    private final Array<Citizen> targets; //Array of citizens targeted by the gossip
    private Array<Citizen> shareChain; //Array of citizens who shared the gossip
    private Array<Citizen> rememberedBy; //Array of citizens who remember the gossip

    //CONSTRUCTOR
    /**
     * A gossip has a creator, description, the people who are involved in the gossip, the sequence of
     * people who tried spreading the gossip and the people who remember the gossip.
     * @param creator the gossip's creator
     * @param targets the people targeted by the gossip
     * @param description the gossip's description
     */
    public GossipClass(Citizen creator, Array<Citizen> targets, String description) {
        this.creator = creator;
        this.description = description;
        this.targets = targets;
        this.shareChain = new ArrayClass<>();
        this.rememberedBy = new ArrayClass<>();
        addToRememberedBy(creator);
    }

    //METHODS
    //Getters
    @Override
    public Citizen getCreator() {return creator;}

    @Override
    public String getDescription() {return description;}

    /**
     * Returns number of gossip's targetted citizens
     * @return number of gossip's targetted citizens
     */
    private int getTargetsSize() {return targets.size();}

    @Override
    public int howManyShares() {return shareChain.size();}
    @Override
    public int countRememberedBy() {return rememberedBy.size();}

    //Boolean
    @Override
    public boolean hasTarget(Citizen citizen) {return targets.searchForward(citizen);}

    /**
     * Checks if the citizen remembers this gossip
     * @param citizen who might or might not remember the gossip
     * @return true if the citizens remembers this gossip
     */
    private boolean rememberedBy(Citizen citizen) {return rememberedBy.searchForward(citizen);}

    @Override
    public boolean isForgotten() {return countRememberedBy() == 0;}

    //Functional
    @Override
    public void addToShareChain(Citizen name) {
        shareChain.insertLast(name);
    }

    @Override
    public void addToRememberedBy(Citizen citizen) {
        if (!rememberedBy(citizen)) {
            rememberedBy.insertLast(citizen);
        }
    }

    @Override
    public void removeFromRememberedBy(Citizen citizen) {
        int index = rememberedBy.searchIndexOf(citizen);
        rememberedBy.removeAt(index);
    }

    @Override
    public boolean compareTo(Citizen otherCreator, Array<Citizen> otherTargets, String otherDescription) {
        boolean same = false;

        if (otherCreator.equals(creator)) { //If same creator
            if (otherDescription.equals(description)) { //If same description
                if (otherTargets.size() == getTargetsSize()) { //If same number of targets

                    Iterator<Citizen> it = otherTargets.iterator();

                    while (it.hasNext() && (!same)) {
                        same = targets.searchForward(it.next()); //If same targets
                    }
                }
            }
        }
        return same;
    }
}