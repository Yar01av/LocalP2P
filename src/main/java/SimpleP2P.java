import files.CharacterArrayFile;
import files.File;
import peers.Peer;
import peers.SimplePeer;
import peers.UnitExchangePeer;
import servers.Server;
import servers.ToyServer;

import java.util.ArrayList;

public class SimpleP2P {
    public static void main(String[] args) {
        ArrayList<Peer> peers = new ArrayList<>();

        UnitExchangePeer p1 = new SimplePeer();
        UnitExchangePeer p2 = new SimplePeer();
        File<Character> file = new CharacterArrayFile(new Character[] {1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10});

        p1.acceptFile(file);
        p2.acceptFile(new CharacterArrayFile(file));
        //p1.assignPartner();
        p2.assignPartner(p1);

        peers.add(p1);
        peers.add(p2);


        Server server = new ToyServer(peers);
        server.distributeAddresses();
        try {
            server.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
