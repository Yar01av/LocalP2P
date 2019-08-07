package shards;


/*
 * A piece of a file that can be copied from it and be used to build up new instances. Parameter T is the type of the
 * input and output that can be put into and taken out of the Shard instance.
 */
abstract public class Shard<T> {
    private final String parentID;

    Shard(String parentID, T dataPiece) {
        this.parentID = parentID;
        saveDataPiece(dataPiece);
    }

    /*
    * Returns the data contained to be used in files
     */
    abstract T getData();

    /*
    * Initializes the data carried by the shard.
     */
    abstract protected void saveDataPiece(T dataPieces);

    public String getParentID() {
        return parentID;
    }
}
