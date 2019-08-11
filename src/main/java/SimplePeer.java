import files.File;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import shards.Shard;
import shards.UnitShard;

import java.util.*;

/*
* Simple peer that runs within the same program as the other peers and can refer to them by reference.
 */
class SimplePeer extends UnitExchangePeer {
    private int N_PARTNERS = 3;
    private List<File> personalFiles;
    private UnitExchangePeer[] partners = new UnitExchangePeer[N_PARTNERS];

    @Override
    void printStatus() {

    }

    @Override
    Collection<String> getPartnersIDs() {
        return null;
    }

    @Override
    void generateFile() {

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

    @Override
    void run() {

    }

    //Find the piece of file<T> that is not in set in this file but is set in that of partner with the same id
    private <T> Pair<Integer, T> getComplementaryElemWithLoc(@NotNull File<T> file, UnitExchangePeer partner) {
        //TODO: make sure that the peer has the given file
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
}
