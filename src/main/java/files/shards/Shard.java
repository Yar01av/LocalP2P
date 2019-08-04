package files.shards;

import org.jetbrains.annotations.Contract;

/*
 * A piece of a file that can be copied from it and be used to build up new instances. Parameter IO is the type of the
 * input and output that can be put into and taken out of the Shard instance.
 */
abstract public class Shard<IO> {
    private final int parentID;

    Shard(int parentID, IO dataPiece) {
        this.parentID = parentID;
        saveDataPiece(dataPiece);
    }

    /*
    * Returns the data contained to be used in files
     */
    abstract IO getData();

    /*
    * Initializes the data carried by the shard.
     */
    abstract void saveDataPiece(IO dataPieces);

    public int getParentID() {
        return parentID;
    }
}
