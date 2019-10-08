package peers;

import files.File;
import shards.Shard;
import shards.SimpleShard;

import java.util.List;
import java.util.Set;

/*
* A peer that sends the index of an element in the file and expects that of other peers. It only communicates with other
* UnitExchangePeer's snd it exchanges data in a form of SimpleShards that carry one element of the sequence of the file.
* Knowledge of a new file is distributes by asking the partners.
 */
public abstract class UnitExchangePeer extends Peer {
    /*
     * Accept a request for a piece of the file and returns either that piece (of type T) or null if that location is
     * undefined.
     */
    private final int N_STEPS_TO_TERMINATION = 100;

    abstract <T> SimpleShard<T> acceptHoleMessage(SimpleShard<Integer> incomingMessage);

    abstract List<UnitExchangePeer> getPartners();

    @Override
    public void run() {
        System.out.println("Starting peer - " + getId());

        //Loop over timesteps
        for (int i = 0; i < N_STEPS_TO_TERMINATION; i++) {
            System.out.println("peers.Peer (" + getId() + ") is going for another round.");

            //Learn about any unknown files
            System.out.println("Peer (" + getId() + ") knows the following files: " + getStoredFiles());
            gainFilesKnowledgeFrom(getPartners());

            //Loop over the files possessed by the peer
            for (File file : getStoredFiles()) {
                if (file.portionComplete() > 0.0) {
                    collectFromPartners(file, getPartners());
                }
            }
        }

        for (File f : getStoredFiles()) {
            System.out.println("Peer (" + getId() + ") is finished!");
            System.out.println("Peer (" + getId() + ") has " + (1.0 - f.portionComplete()) + " of " + f.getId());
        }
    }

    abstract protected void gainFilesKnowledgeFrom(List<? extends Peer> peers);

    abstract protected void collectFromPartners(File file, List<? extends UnitExchangePeer> partners);

    //TODO: lift up if possible
    abstract public void assignPartner(UnitExchangePeer peer);
}
