package shards;

import org.apache.commons.lang3.ObjectUtils;

/*
* Shard that carries a single object inside of it.
 */
public class UnitShard<T> extends Shard<T> {
    private T data;

     public UnitShard(String parentID, T dataPieces) {
        super(parentID, dataPieces);
    }

    @Override
    T getData() {
        return ObjectUtils.cloneIfPossible(data);
    }

    @Override
    protected void saveDataPiece(T dataPiece) {
        data = ObjectUtils.cloneIfPossible(dataPiece);
    }
}
