package files;

import shards.Shard;

import java.util.*;

/*
* Represents a file using an array of char.
* TODO: generalize the type of array files
 */
public class CharacterArrayFile extends File<Character> {
    private final Character[] content;

    public CharacterArrayFile(int size) {
        content = new Character[size];
    }

    public CharacterArrayFile(Character[] content) {
        this.content = content.clone();
    }

    /*
    * Derivative constructor that creates an empty copy of the file
    * //TODO: replace with a .cleanCopy() method
     */
    public CharacterArrayFile(File file) {
        super(file.getId());
        this.content = new Character[file.getSize()];
    }

    void fillFileWith(Collection<Character> content) {
        moveContent(content, this.content);
    }

    void fillFileWithRandom(int length) {
        Character[] generatedContent = makeRandomArray(length);
        moveContent(generatedContent, this.content);
    }

    @Override
    public java.io.File extractFile() {
        return null; // TODO
    }

    @Override
    public double portionComplete() {
        int nullCount = 0;

        for (Character ch : content) {
            if (ch == null) nullCount++;
        }

        return ((double) nullCount)/content.length;
    }

    private boolean checkNull(char ch) {
        return ch == 0;
    }

    @Override
    public int getSize() {
        return content.length;
    }

    @Override
    public Character getElementAt(int index) {
        return content[index];
    }

    @Override
    public void setElementAt(int index, Character element) {
        content[index] = element;
    }

    @Override
    public String toString() {
        return content.toString();
    }

    private Character[] makeRandomArray(int length) {
        Random r = new Random();
        Character[] output = new Character[length];


        //Fill the output
        for (int i = 0; i<length; i++) {
            output[i] = (char)(r.nextInt() + 'a');
        }

        return output;
    }

    // Moves the content of from collection to the to array. Their lengths should be the same.
    private void moveContent(Collection<Character> from, Character[] to) {
        int i = 0;

        for (Character c : from) {
            to[i] = c;
            i++;
        }
    }

    private void moveContent(Character[] from, Character[] to) {
        List<Character> listFrom = Arrays.asList(from);

        moveContent(listFrom, to);
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (obj instanceof CharacterArrayFile) {
//            return Arrays.equals(((CharacterArrayFile) obj).content, this.content);
//        } else {
//            return super.equals(obj);
//        }
//    }
}
