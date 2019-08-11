import files.File;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import shards.UnitShard;

/*
* A peer that sends the index of an element in the file and expects that
 */
public abstract class UnitExchangePeer extends Peer {
    /*
     * Accept a request for a piece of the file and returns either that piece (of type T) or null if that location is
     * undefined.
     */
    abstract <T> UnitShard<T> acceptHoleMessage(UnitShard<Integer> incomingMessage);
}
