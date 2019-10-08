package peers;

import files.File;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import shards.Shard;
import shards.SimpleShard;

import java.util.*;

//TODO: lift up as much as possible
public class SimplePeer extends UnitExchangePeer {
    //private int N_PARTNERS = 3;
    private Set<File> personalFiles = new HashSet<>();
    private List<UnitExchangePeer> partners = new ArrayList<>();

    @Override
    void printStatus() {
        for (File f : personalFiles) {
            System.out.println("peers.Peer (" + getId() + ") has " + (1.0 - f.portionComplete()) + " of " + f.getId());
        }
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

    @Override
    public List<File> getStoredFiles() {
        return new ArrayList<>(personalFiles);
    }

    @Override
    public List<UnitExchangePeer> getPartners() {
        //It is important to not clone the partners!
        return partners;
    }

    /*
     * Accept a request for a piece of the file<T> and returns either that piece or null if that location is undefined
     */
    @Override
    <T> SimpleShard<T> acceptHoleMessage(SimpleShard<Integer> incomingMessage) {
        int desiredIndex;
        T returnData = null;

        //Search for the right file
        for (File f : personalFiles) {
            if (f.getId().equals(incomingMessage.getParentID())) {
                desiredIndex = incomingMessage.getData();
                returnData = (T) f.getElementAt(desiredIndex); // If files share IDs, they also share types!
            }
        }

        return new SimpleShard<T>(incomingMessage.getParentID(), returnData);
    }

    @Override
    public Shard<Set<File>> getFilesKnowledge() {
        return new SimpleShard<Set<File>>(getId(), new HashSet<File>(personalFiles));
    }

    protected void gainFilesKnowledgeFrom(List<? extends Peer> peers) {
        for (Peer peer : peers) {
            Set<File> filesKnownByPeer = peer.getFilesKnowledge().getData();

            personalFiles.addAll(setDifferenceOnID(filesKnownByPeer, personalFiles));
        }
    }

    //Set operation A - B
    private Set<File> setDifferenceOnID(Set<File> A, Set<File> B) {
        A = new HashSet<>(A);
        B = new HashSet<>(B);

        for (File f1 : A) {
            for (File f2 : B) {
                if (f1.getId().equals(f2.getId())) {
                    A.remove(f1);
                }
            }
        }

        return A;
    }

    //Find the piece of file<T> that is not in set in this file but is set in that of partner with the same id
    private <T> Pair<Integer, T> getComplementaryElemWithLoc(File<T> file, UnitExchangePeer partner) {
        List<Integer> nullElemsLocations = getNulls(file); //Search for nulls
        Collections.shuffle(nullElemsLocations);  //Randomize for more efficient shard distribution

        //Request all null locations until successful response
        for (int i : nullElemsLocations) {
            SimpleShard<Integer> emptyLocation = new SimpleShard<>(file.getId(), i);
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

    //Get data about the file from the partners
    protected void collectFromPartners(File file, List<? extends UnitExchangePeer> partners) {
        for (UnitExchangePeer peer : partners) {
            if (peer.getFilesIDs().contains(file.getId())) {
                Pair<Integer, Object> compElement = getComplementaryElemWithLoc(file, peer);

                if (compElement == null) {
                    break;
                }

                file.setElementAt(compElement.getLeft(), compElement.getRight());

                printStatus();
            }
        }
    }
}
