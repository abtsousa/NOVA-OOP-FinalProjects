package community.citizen;

/**
 *  Gossipers never forget a gossip, and they can share multiple gossips
 */
public class CitizenGossiper extends CitizenAbstract implements Citizen {

    /**
     * Gossipers never forget a gossip, and they can share multiple gossips
     * @param name the citizen's name
     */
    public CitizenGossiper(String name) {
        super(name);
        this.loquacity = 3;
    }

    //METHODS
    @Override
    public String toString() {return (name + " is a gossiper.");}
}