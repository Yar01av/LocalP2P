import shards.Shard;

import java.util.UUID;

/*
* This class encapsulates entities able to communicate with each other by exchanging Shards and the exact protocols they
* use for that. Each peer has partners.
* whom it uses to get pieces of its desired file.
 */
abstract class Peer {
    private final String id;

    Peer() {
        id = "Peer-" + UUID.randomUUID().toString();
    }

    abstract void printStatus();

    abstract void getPartners();

    abstract void generateFile();

    /*
    * Accept message from a different peer
     */
    abstract void acceptMessage(Shard incomingMessage);

    /*
    * Activates the thread of this peer.
     */
    abstract void run();

    public String getId() {
        return id;
    }
}
