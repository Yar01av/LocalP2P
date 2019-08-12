package servers;

import peers.Peer;

import java.util.List;

/*
* The instance coordinates everything that needs to be done centrally. It must know about all peers in the network.
 */
public abstract class Server {
    /*
    * Distributes the addresses/reference of one other peer to every peer it knows
     */
    public abstract void distributeAddresses();

    public abstract void addNewPeer(Peer peer);

    public abstract List<Peer> getPeers();

    /*
    * Activates the peers and makes them run in parallel.
     */
    public abstract void run() throws InterruptedException;
}
