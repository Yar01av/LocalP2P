import files.File;
import shards.Shard;

/*
* Simple peer that runs within the same program as the other peers and can refer to them by reference.
 */
class SimplePeer extends Peer {
    private int N_PARTNERS = 3;
    private File[] personalFiles;

    @Override
    void printStatus() {

    }

    @Override
    void getPartners() {

    }

    @Override
    void generateFile() {

    }

    @Override
    void acceptMessage(Shard<Character> incomingMessage) {

    }


    @Override
    void run() {

    }

    private void requestEmptyFileSection(File file, SimplePeer partner) {
        //TODO: make sure that the peer has the given file

        Shard emptyPiece = file.getEmptyShard(); //Get an empty piece of file

    }
}
