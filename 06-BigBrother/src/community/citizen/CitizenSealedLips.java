package community.citizen;
import community.*;
import dataStructures.*;

/**
 * Sealed lips never forget a gossip, but they only share them if they're about themselves
 */
public class CitizenSealedLips extends CitizenAbstract implements CitizenNarcissist {

    /**
     * Sealed lips never forget a gossip, but they only share them if they're about themselves
     * @param name the citizen's name
     */
    public CitizenSealedLips(String name) {
        super(name);
        this.loquacity = 1;
    }

    //METHODS
    @Override
    public String toString() {
        return (name + " lips are sealed.");
    }

    @Override
    public Iterator<Gossip> listShareableGossips() {
        return new OffsetIterator<>(getShareableGossips(),nextGossipIndex);
    }

    /**
     * Filters rememberedGossips by whether they're about the citizen or not
     * @return array with gossips that this citizen can share
     */
    private Array<Gossip> getShareableGossips() {
        Array<Gossip> filter = new ArrayClass<>();
        Iterator<Gossip> it = listRememberedGossips();

        while (it.hasNext()) {
            Gossip g = it.next();
            if (g.hasTarget(this)) filter.insertLast(g);
        }
        return filter;
    }
}