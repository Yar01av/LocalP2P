import files.File;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import shards.Shard;
import shards.UnitShard;

import java.util.*;

/*
* Simple peer that runs within the same program as the other peers and can refer to them by reference.
 */
class SimplePeer extends UnitExchangePeer {
    //private int N_PARTNERS = 3;
    private List<File> personalFiles = new ArrayList<>();
    private List<UnitExchangePeer> partners = new ArrayList<>();
    private final int N_STEPS_TO_TERMINATION = 100;

    @Override
    void printStatus() {
        for (File f : personalFiles) {
            System.out.println("Peer (" + getId() + ") has " + (1.0 - f.portionComplete()) + " of " + f.getId());
        }
    }

    @Override
    Collection<String> getPartnersIDs() {
        return null;
    }

    @Override
    void generateFile() {

    }

    @Override
    public List<String> getFilesIDs() {
        List<String> output = new ArrayList<>();

        for (File f : personalFiles) {
            output.add(f.getId());
        }

        return output;
    }

    @Override
    public void assignPartner(UnitExchangePeer peer) {
        partners.add(peer);
    }

    @Override
    public void acceptFile(File file) {
        personalFiles.add(file);
    }

    /*
     * Accept a request for a piece of the file<T> and returns either that piece or null if that location is undefined
     */
    @Override
    <T> UnitShard<T> acceptHoleMessage(UnitShard<Integer> incomingMessage) {
        int desiredIndex;
        T returnData = null;

        //Search for the right file
        for (File f : personalFiles) {
            if (f.getId().equals(incomingMessage.getParentID())) {
                desiredIndex = incomingMessage.getData();
                returnData = (T) f.getElementAt(desiredIndex); // If files share IDs, they also share types!
            }
        }

        return new UnitShard<T>(incomingMessage.getParentID(), returnData);
    }

    //Find the piece of file<T> that is not in set in this file but is set in that of partner with the same id
    private <T> Pair<Integer, T> getComplementaryElemWithLoc(File<T> file, UnitExchangePeer partner) {
        List<Integer> nullElemsLocations = getNulls(file); //Search for nulls
        Collections.shuffle(nullElemsLocations);  //Randomize for more efficient shard distribution

        //Request all null locations until successful response
        for (int i : nullElemsLocations) {
            UnitShard<Integer> emptyLocation = new UnitShard<>(file.getId(), i);
            Shard<T> response = partner.acceptHoleMessage(emptyLocation);

            if (response != null) {
                return new ImmutablePair<>(i, response.getData());
            }
        }

        return null;
    }

    //Search for locations of null elements
    private List<Integer> getNulls(File file) {
        List<Integer> nullElemsLocations = new ArrayList<>();

        for (int i = 0; i < file.getSize(); i++) {
            Object elem = file.getElementAt(i);

            //Add the location of the null element
            if (elem == null) {
                nullElemsLocations.add(i);
            }
        }

        return nullElemsLocations;
    }

    @Override
    public void run() {
        System.out.println("Starting peer - " + getId());

        //Loop over timesteps
        for (int i = 0; i < N_STEPS_TO_TERMINATION; i++) {
            System.out.println("Peer (" + getId() + ") is going for another round.");

            //Loop over the files possessed by the peer
            for (File file : personalFiles) {
                if (file.portionComplete() > 0.0) {
                    collectFromPartners(file, partners);
                }
            }
        }

        for (File f : personalFiles) {
            System.out.println("Peer (" + getId() + ") has " + (1.0 - f.portionComplete()) + " of " + f.getId());
        }
    }

    //Get data about the file from the partners
    private void collectFromPartners(File file, List<UnitExchangePeer> partners) {
        for (UnitExchangePeer peer : partners) {
            if (peer.getFilesIDs().contains(file.getId())) {
                Pair<Integer, Object> compElement = getComplementaryElemWithLoc(file, peer);

                if (compElement == null) {
                    break;
                }

                printStatus();
            }
        }
    }
}
