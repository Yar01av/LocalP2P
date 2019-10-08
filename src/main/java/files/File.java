package files;

import shards.Shard;

import java.util.*;

/*
* File encapsulates the data being distibuted by the network. This can for instance be .txt files or byte arrays.
* It is assumed that the file can be represented by a sequence. and the id needs to be unique. T denotes the type of
* elements that the sequence consists of.
 */
abstract public class File<T> {
    // TODO: make it possible to extract data from the file about what is filled and what isn't

    private final String id;

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
    * Returns the number of elements in the underlying sequence
     */
    abstract public int getSize();

    abstract public T getElementAt(int index);

    abstract public void setElementAt(int index, T element);

    public List<T> getListOfContent() {
        List<T> output = new ArrayList<>();

        for (int i = 0; i < getSize(); i++) {
            output.add(getElementAt(i));
        }

        return output;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof File) {
            for (int i = 0; i < ((File) obj).getSize(); i++) {
                if (((File) obj).getElementAt(i) != getElementAt(i)) {
                    return false;
                }
            }

            return true;
        } else {
            return super.equals(obj);
        }
    }
}
