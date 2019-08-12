package servers;

import peers.Peer;
import servers.Server;

import java.util.ArrayList;
import java.util.List;

/*
* Simple server that exists locally with all the peers
 */
public class ToyServer extends Server {
    private final List<Peer> peers;

    public ToyServer() {
        peers = new ArrayList<Peer>();
    }

    public ToyServer(List<Peer> initPeers) {
        peers = initPeers;
    }

    @Override
    public void distributeAddresses() {

    }

    @Override
    public void addNewPeer(Peer peer) {
        peers.add(peer);
    }

    public List<Peer> getPeers() {
        return new ArrayList<>(peers);
    }

    @Override
    public void run() throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (Peer p : peers) {
            Thread thread = new Thread(p);
            threads.add(thread);
            thread.start();
        }

        //The server terminates ones all of its threads do
        for (Thread t : threads) {
            t.join();
        }
    }
}
