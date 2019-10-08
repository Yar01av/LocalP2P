package shards;

import org.apache.commons.lang3.ObjectUtils;

public class SimpleShard<T> extends Shard<T> {
    private T data;

     public SimpleShard(String parentID, T dataPieces) {
        super(parentID, dataPieces);
    }

    @Override
    public T getData() {
        return ObjectUtils.cloneIfPossible(data);
    }

    @Override
    protected void saveDataPiece(T dataPiece) {
        data = ObjectUtils.cloneIfPossible(dataPiece);
    }
}
