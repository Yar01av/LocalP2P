import files.File;
import shards.Shard;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/*
* This class encapsulates entities able to communicate with each other by exchanging Shards and the exact protocols they
* use for that. Each peer has partners whom it uses to get pieces of its desired file.
 */
abstract class Peer implements Runnable {
    private final String id;

    Peer() {
        id = "Peer-" + UUID.randomUUID().toString();
    }

    abstract void printStatus();

    abstract Collection<String> getPartnersIDs();

    abstract void generateFile();

    abstract public List<String> getFilesIDs();

    //Replace with the protocol
    abstract public void acceptFile(File file);

    public String getId() {
        return id;
    }
}
