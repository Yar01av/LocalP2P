package peers;

import shards.UnitShard;

/*
* A peer that sends the index of an element in the file and expects that of other peers. It only communicates with other
* UnitExchangePeer's snd it exchanges data in a form of UnitShards. Knowledge of a new file is distributes by asking the
* partners.
 */
public abstract class UnitExchangePeer extends Peer {
    /*
     * Accept a request for a piece of the file and returns either that piece (of type T) or null if that location is
     * undefined.
     */
    abstract <T> UnitShard<T> acceptHoleMessage(UnitShard<Integer> incomingMessage);

    abstract public void getFilesKnowledge();

    //TODO: lift up if possible
    abstract public void assignPartner(UnitExchangePeer peer);
}
