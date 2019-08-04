import java.util.ArrayList;

public class SimpleP2P {
    public static void main(String[] args) {
        ArrayList<Peer> peers = new ArrayList<>();
        peers.add(new SimplePeer());
        peers.add(new SimplePeer());

        Server server = new ToyServer(peers);
        server.distributeAddresses();
    }
}
