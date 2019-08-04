package files;

import com.sun.istack.internal.NotNull;
import files.shards.UnitShard;
import files.shards.Shard;

import java.util.Random;

/*
* Represents a file using an array of char.
* TODO: generalize the type of array files
 */
class CharFile extends File {
    private final char[] content;
    protected final int DEFAULT_LENGTH = 100;

    public CharFile() {
        content = new char[DEFAULT_LENGTH];
    }

    public CharFile(int size) {
        content = new char[size];
    }

    public CharFile(@NotNull char[] content) {
        this.content = content.clone();
    }

    void fillFileWith(@NotNull char[] content) {
        moveContent(content, this.content);
    }

    void fillFileWithRandom(int length) {
        char[] generatedContent = makeRandomArray(length);
        moveContent(generatedContent, this.content);
    }

    @Override
    public java.io.File extractFile() {
        return null; // TODO
    }

    @Override
    public double portionComplete() {
        int nullCount = 0;

        for (char ch : content) {
            if (checkNull(ch)) nullCount++;
        }

        return nullCount;
    }

    private boolean checkNull(char ch) {
        return ch == 0;
    }

    @Override
    public void setContent(Shard data) {

    }

    @Override
    public Shard getShard() {
        return null;
    }

    @Override
    UnitShard[] splitToShards(int nOutput) {
        return null;
    }

    @Override
    public String toString() {
        return content.toString();
    }

    private char[] makeRandomArray(int length) {
        Random r = new Random();
        char[] output = new char[length];


        //Fill the output
        for (int i = 0; i<length; i++) {
            output[i] = (char)(r.nextInt() + 'a');
        }

        return output;
    }

    // Moves the content of from array to the to array. THeir lengths should be the same.
    private void moveContent(char[] from, char[] to) {
        for (int i = 0; i < from.length; i++) to[i] = from[i];
    }
}
