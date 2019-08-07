package files;

import com.sun.istack.internal.NotNull;
import shards.Shard;
import shards.UnitShard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
* Represents a file using an array of char.
* TODO: generalize the type of array files
 */
public class SimpleCharFile extends File {
    private final char[] content;

    public SimpleCharFile(int size) {
        content = new char[size];
    }

    public SimpleCharFile(@NotNull char[] content) {
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
    public Shard getEmptyShard() {
        Random random = new Random();
        List<Integer> listOfNullVals = new ArrayList<>();

        //Find all undefined places
        for (int i = 0; i < content.length; i++) {
            if (content[i] == (char) 0) listOfNullVals.add(i);
        }

        int randomIndex = random.nextInt(listOfNullVals.size()); //Choose a random empty place

        return new UnitShard<Integer>(id, listOfNullVals.get(randomIndex));
    }

    @Override
    public int getSize() {
        return content.length;
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
