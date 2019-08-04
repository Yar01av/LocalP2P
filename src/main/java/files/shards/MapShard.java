package files.shards;

import java.util.HashMap;
import java.util.Map;

public class MapShard<IO> extends Shard<Map<Integer, IO>> {
    private Map<Integer, IO> data;

    MapShard(int parentID, Map<Integer, IO> dataPieces) {
        super(parentID, dataPieces);
    }

    @Override
    Map<Integer, IO> getData() {
        return new HashMap<Integer, IO>(data);
    }

    @Override
    void saveDataPiece(Map<Integer, IO> dataPiece) {
        data = new HashMap<>(dataPiece);
    }
}
