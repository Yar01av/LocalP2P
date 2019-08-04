import java.util.ArrayList;
import java.util.Collection;

/*
* The instance coordinates everything that needs to be done centrally. It must know about all peers in the network.
 */
abstract class Server {
    /*
    * Distributes the addresses/reference of one other peer to every peer it knows
     */
    abstract void distributeAddresses();

    abstract void addNewPeer(Peer peer);

    abstract ArrayList<Peer> getPeers();

    /*
    * Activates the peers and makes them run in parallel.
     */
    abstract void run();
}
