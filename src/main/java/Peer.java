import java.util.UUID;

/*
* This class encapsulates entities able to communicate with each other by exchanging Shards. Each peer has partners
* whom it uses to get pieces of its desired file.
 */
abstract class Peer {
    private final String id;

    Peer() {
        id = UUID.randomUUID().toString();
    }

    abstract void printStatus();

    abstract void getPartners();

    abstract void generateFile();

    abstract void run();
}
