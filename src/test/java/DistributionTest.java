import files.File;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.junit.Before;
import org.junit.Test;
import peers.Peer;
import peers.UnitExchangePeer;
import servers.Server;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

abstract public class DistributionTest {
    private Server server;

    @Before
    public void setUp() throws Exception {
        server = getServer();
    }

    abstract Server getServer();

    abstract UnitExchangePeer getPeer();

    abstract File getFile();

    abstract File clearCopyFile(File file);

    //1 to 1 transfer
    @Test
    public void transferTest1() throws InterruptedException {
        List<Peer> peers = new ArrayList<>();

        UnitExchangePeer p1 = getPeer();
        UnitExchangePeer p2 = getPeer();
        File file = getFile();

        p1.acceptFile(file);
        p2.acceptFile(clearCopyFile(file));

        p2.assignPartner(p1);

        server.addNewPeer(p1);
        server.addNewPeer(p2);

        server.run();

        comparePeers(server.getPeers());
    }

    //1 to 2 transfer
    @Test
    public void transferTest2() throws InterruptedException {
        List<Peer> peers = new ArrayList<>();

        UnitExchangePeer p1 = getPeer();
        UnitExchangePeer p2 = getPeer();
        UnitExchangePeer p3 = getPeer();
        File file = getFile();

        p1.acceptFile(file);
        p2.acceptFile(clearCopyFile(file));
        p3.acceptFile(clearCopyFile(file));

        p2.assignPartner(p1);
        p3.assignPartner(p1);

        server.addNewPeer(p1);
        server.addNewPeer(p2);
        server.addNewPeer(p3);

        server.run();

        comparePeers(server.getPeers());
    }

    //1 to 1 transfer (with possible back-flow)
    @Test
    public void transferTest3() throws InterruptedException {
        List<Peer> peers = new ArrayList<>();

        UnitExchangePeer p1 = getPeer();
        UnitExchangePeer p2 = getPeer();
        File file = getFile();

        p1.acceptFile(file);
        p2.acceptFile(clearCopyFile(file));

        p1.assignPartner(p2);
        p2.assignPartner(p1);

        server.addNewPeer(p1);
        server.addNewPeer(p2);

        server.run();

        comparePeers(server.getPeers());
    }

    //1 to 1 to 1 transfer (with back-flow)
    @Test
    public void transferTest4() throws InterruptedException {
        List<Peer> peers = new ArrayList<>();

        UnitExchangePeer p1 = getPeer();
        UnitExchangePeer p2 = getPeer();
        UnitExchangePeer p3 = getPeer();
        File file = getFile();

        p1.acceptFile(file);
        p2.acceptFile(clearCopyFile(file));
        p3.acceptFile(clearCopyFile(file));

        p2.assignPartner(p1);
        p1.assignPartner(p2);
        p3.assignPartner(p2);
        p2.assignPartner(p3);


        server.addNewPeer(p1);
        server.addNewPeer(p2);
        server.addNewPeer(p3);

        server.run();

        comparePeers(server.getPeers());
    }

    //Circle of 3
    @Test
    public void transferTest5() throws InterruptedException {
        List<Peer> peers = new ArrayList<>();

        UnitExchangePeer p1 = getPeer();
        UnitExchangePeer p2 = getPeer();
        UnitExchangePeer p3 = getPeer();
        File file = getFile();

        p1.acceptFile(file);
        p2.acceptFile(clearCopyFile(file));
        p3.acceptFile(clearCopyFile(file));

        p2.assignPartner(p1);
        p3.assignPartner(p2);
        p1.assignPartner(p3);

        server.addNewPeer(p1);
        server.addNewPeer(p2);
        server.addNewPeer(p3);

        server.run();

        comparePeers(server.getPeers());
    }

    private void comparePeers(List<Peer> peers) {
        int peersToCompare = 2;  //Number of peers to compare per turn

        Iterator<int[]> iterator = CombinatoricsUtils.combinationsIterator(peers.size(), peersToCompare);
        while (iterator.hasNext()) {
            final int[] combination = iterator.next();

            //Compare the peers at the indices of the numeric combination
            if (!(peers.get(combination[0]).getStoredFiles().equals(peers.get(combination[1]).getStoredFiles()))) {
                fail();
            }
        }
    }
}