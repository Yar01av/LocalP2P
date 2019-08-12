import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/*
* Simple server that exists locally with all the peers
 */
class ToyServer extends Server {
    private final List<Peer> peers;

    ToyServer() {
        peers = new ArrayList<Peer>();
    }

    ToyServer(List<Peer> initPeers) {
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
            new Thread(p).start();
        }
    }
}
