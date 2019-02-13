package com.gmail.nossr50.core.data.blockmeta.chunkmeta;

import com.gmail.nossr50.core.mcmmo.world.World;

public class ChunkStoreFactory {
    protected static ChunkStore getChunkStore(World world, int x, int z) {
        // TODO: Add in loading from config what type of store we want.
        return new PrimitiveChunkStore(world, x, z);
    }
}
