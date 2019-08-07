package files;

import shards.Shard;

import java.util.UUID;

/*
* File encapsulates the data being distibuted by the network. This can for instance be .txt files or byte arrays.
* It is assumed that the file can be represented by a sequence. and the id needs to be unique. A denotes the type of
* that sequence.
 */
abstract public class File {
    protected final String id;

    /*
    * Constructor to be used for creating completely new files
    */
    File() {
        id = "File-" + UUID.randomUUID().toString();
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
    abstract public java.io.File extractFile();

    /*
    * How much of a complete file does this instance contain.
     */
    abstract public double portionComplete();

    /*
    * Sets the content of the file
    *
    * @param data - part of the complete file to use
     */
    abstract public void setContent(Shard data);

    /*
    * Copies a piece of file for distribution.
     */
    abstract public Shard getShard() throws IllegalStateException;

    /*
     * Wraps an empty piece of file in a shard (e.g. index of one of nulls if the file is an array)
     */
    abstract public Shard getEmptyShard() throws IllegalStateException;

    /*
    * Returns the number of elements in the underlying sequence
     */
    abstract public int getSize();
}
