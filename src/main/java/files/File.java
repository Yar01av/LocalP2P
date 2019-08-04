package files;

import files.shards.UnitShard;
import files.shards.Shard;

import java.util.UUID;

/*
* File encapsulates the data being distibuted by the network. This can for instance be .txt files or byte arrays.
* It is assumed that the file can be represented by a sequence. and the id needs to be unique. A denotes the type of
* that sequence.
 */
abstract class File {
    private final String id;

    /*
    * Constructor to be used for creating completely new files
    */
    File() {
        id = UUID.randomUUID().toString();
    }

    /*
     * Constructor to be used for refering to existing files
     */
    public File(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    /*
    * Gets the file for later use outside of the program.
     */
    public abstract java.io.File extractFile();

    /*
    * How much of a complete file does this instance contain.
     */
    public abstract double portionComplete();

    /*
    * Sets the content of the file
    *
    * @param data - part of the complete file to use
     */
    public abstract void setContent(Shard data);

    public abstract Shard getShard();

    /*
     * Splits the file into shards for distribution
     *
     * @param file - file to split
     * @param nOutput - number of shards to split into
     * @output - List of shards
     */
    abstract UnitShard[] splitToShards(int nOutput);
}
