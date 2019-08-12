package peers;

import files.File;
import shards.Shard;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/*
* This class encapsulates entities able to communicate with each other by exchanging Shards and the exact protocols they
* use for that. Each peer has partners whom it uses to get pieces of its desired file.
 */
public abstract class Peer implements Runnable {
    private final String id;

    Peer() {
        id = "peers.Peer-" + UUID.randomUUID().toString();
    }

    abstract void printStatus();

    abstract Collection<String> getPartnersIDs();

    abstract void generateFile();

    abstract public List<String> getFilesIDs();

    //TODO: Replace with the protocol
    /*
    * Grants this peer a file. It is assumed that the file consists of at least one element
     */
    abstract public void acceptFile(File file);

    public String getId() {
        return id;
    }

    abstract public List<File> getStoredFiles();
}
