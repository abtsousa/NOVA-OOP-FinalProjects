package community.citizen;

import community.Gossip;
import dataStructures.Iterator;

/**
 * Narcissistic citizens only share gossips about themselves
 * Sealed lips citizens are narcissistic by nature
 */
public interface CitizenNarcissist extends Citizen {
    /**
     * Lists only the gossips about the gossiper
     * @return iterator with only gossips about the gossiper
     */
    Iterator<Gossip> listShareableGossips();
}
