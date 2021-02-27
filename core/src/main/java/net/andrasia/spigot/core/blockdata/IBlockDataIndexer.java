package net.andrasia.spigot.core.blockdata;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nonnull;

/**
 * This is a utility class, used to convert blockstates to integers, and vise versa.
 * This can be useful in multiple scenarios like compressing or simplifying storing it.
 *
 * This is also useful when the only way to represent data is using primitives, like the PersistentDataContainer.
 */
public interface IBlockDataIndexer
{
    /**
     * @param blockData Converting existing blockdata to an integer
     * @return The integer index.
     */
    int getIndex(@Nonnull BlockData blockData);

    /**
     * Reconstructs blockdata from a given index. This function is O(0) the second time its called. (Caching)
     * OutOfBoundsException will be thrown if index < 0 or index >= max()
     *
     * @param index Index to convert to blockdata. Must be between (0, max()]
     * @return Reconstructed Blockdata.
     */
    @Nonnull
    BlockData fromIndex(int index);

    /**
     * @return Returns the biggest index possible (exclusive).
     */
    int max();

    /**
     * @return Returns the blockdata type its able to convert.
     */
    @Nonnull
    Class<? extends BlockData> getBlockDataClass();

    /**
     * Clones the current indexer but with another material used. This is needed because BlockData stores the material
     * was created with. Dumb spigot.
     * @param material Material to use
     * @return Returns a new indexer for the same type.
     */
    @Nonnull
    IBlockDataIndexer createNew(@Nonnull Material material);
}
