package community.citizen;

/**
 * CitizenLimitedMemory
 * Methods that manage citizen objects with limited memory
 * Limited memory affects many methods regarding gossip management
 */
public interface CitizenLimitedMemory extends Citizen {

    /**
     * Citizens with limited memory reset back to the oldest gossip when they move groups.
     * Resets shareable gossip index to the oldest gossip.
     */
    void resetGossipIndex();
}
