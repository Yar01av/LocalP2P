import files.CharacterArrayFile;
import files.File;
import peers.SimplePeer;
import peers.UnitExchangePeer;
import servers.Server;
import servers.ToyServer;

public class DistributionTestImpl1 extends DistributionTest {
    @Override
    Server getServer() {
        return new ToyServer();
    }

    @Override
    UnitExchangePeer getPeer() {
        return new SimplePeer();
    }

    @Override
    File getFile() {
        return new CharacterArrayFile(new Character[] {1, 2, 3, 4, 5, 5, 6, 7, 8, 9, 10});
    }

    @Override
    File clearCopyFile(File file) {
        return new CharacterArrayFile(file);
    }
}
