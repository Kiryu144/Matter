package net.andrasia.spigot.core.blockdata;

import org.bukkit.block.data.BlockData;

import javax.annotation.Nonnull;
import java.lang.reflect.Array;

/**
 * This extension of the IXBlockDataIndexer caches all indicies possible for a given blockdata.
 * This is just a performance increase for the cost of a little memory.
 */
public abstract class CachedBlockDataIndexer implements IBlockDataIndexer
{
    private BlockData[] cachedBlockData = null;

    /**
     * Converts the given index to blockdata. Must return a valid state.
     * This is only used for caching purposes and shouldn't be called from outside.
     * The given index must fit between (0, max()]
     *
     * @param index Index to reconstruct.
     * @return Am valid blockdata from the given index.
     */
    @Nonnull
    abstract BlockData createForIndex(int index);

    @Nonnull
    @Override
    public BlockData fromIndex(int index)
    {
        if (this.cachedBlockData != null)
        {
            return this.cachedBlockData[index];
        }
        else
        {
            this.cachedBlockData = (BlockData[]) Array.newInstance(this.getBlockDataClass(), this.max());
            for (int i = 0; i < this.max(); ++i)
            {
                this.cachedBlockData[i] = this.createForIndex(i);
            }
        }
        return this.fromIndex(index);
    }
}
