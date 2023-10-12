package community.citizen;

import community.Gossip;

/**
 * Forgetful citizens have limited memory
 * Once the memory limit is hit, they forget the oldest gossip
 */
public class CitizenForgetful extends CitizenAbstract implements CitizenLimitedMemory {

    //CONSTANTS
    private final int memory; //the memory limit

    /**
     *  Forgetful citizens have limited memory
     *  Once the memory limit is hit, they forget the oldest gossip
     * @param name the citizen's name
     * @param memory how many gossips they remember
     */
    public CitizenForgetful(String name, int memory) {
        super(name);
        this.memory = memory;
        this.loquacity = 1;
    }

    //METHODS
    @Override
    public String toString() {
        return (name + " can only remember up to " + memory + " gossips.");
    }

    @Override
    public void hearGossip(Gossip gossip) {
        if (!rememberedGossips.searchForward(gossip)) { //if the citizen doesn't remember the gossip
            while (countRememberedGossips() >= memory) { //if memory limit has been hit

                Gossip g = rememberedGossips.get(0);
                g.removeFromRememberedBy(this);

                rememberedGossips.removeAt(0); //forgets the oldest gossip
                if (nextGossipIndex > 0) {
                    nextGossipIndex--;} //keep track of correct sharing index
            }
            rememberedGossips.insertLast(gossip);
            gossip.addToRememberedBy(this);
        }
    }

    @Override
    public void resetGossipIndex() {
        nextGossipIndex = 0;
    }
}