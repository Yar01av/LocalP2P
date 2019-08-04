package files.shards;

/*
* Shard that carries a single character inside it.
* TODO: implement other shards. Consider re-writing this one in terms of integer shard.
 */
public class CharUnitShard extends Shard<Character> {
    private Character data;

    CharUnitShard(int parentID, Character dataPieces) {
        super(parentID, dataPieces);
    }

    @Override
    Character getData() {
        return data;
    }

    @Override
    protected void saveDataPiece(Character dataPiece) {
        data = dataPiece;
    }
}
