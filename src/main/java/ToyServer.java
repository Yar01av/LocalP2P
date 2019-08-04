import java.util.ArrayList;
import java.util.Collection;

/*
* Simple server that exists locally with all the peers
 */
class ToyServer extends Server {
    private final ArrayList<Peer> peers;

    ToyServer() {
        peers = new ArrayList<Peer>();
    }

    ToyServer(ArrayList<Peer> initPeers) {
        peers = initPeers;
    }

    @Override
    void distributeAddresses() {

    }

    @Override
    void addNewPeer(Peer peer) {

    }

    ArrayList<Peer> getPeers() {
        return peers;
    }

    @Override
    void run() {
        for (Peer p : peers) {
            p.run();
        }
    }
}
