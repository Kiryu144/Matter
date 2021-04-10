package at.dklostermann.spigot.matter.blockdata;

import at.dklostermann.spigot.matter.reflection.MaterialReflector;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Tripwire;

import javax.annotation.Nonnull;

public class TripwireBlockDataIndexer extends CachedBlockDataIndexer
{
    private final Material material;

    /**
     * This constructor creates an invalid state. To create a valid state, use createForIndex().
     */
    public TripwireBlockDataIndexer()
    {
        this.material = null;
    }

    public TripwireBlockDataIndexer(Material material)
    {
        this.material = material;
        Validate.isTrue(MaterialReflector.GetBlockData(material) == this.getBlockDataClass(),
                String.format("Invalid material %s for blockdata of type %s", material,
                        this.getBlockDataClass().getSimpleName()));
    }

    @Override
    public int getIndex(@Nonnull BlockData blockData)
    {
        Tripwire tripwire = (Tripwire) blockData;
        int index = 0;
        index |= tripwire.isAttached() ? 1 << 0 : 0;
        index |= tripwire.isDisarmed() ? 1 << 1 : 0;
        index |= tripwire.hasFace(BlockFace.EAST) ? 1 << 2 : 0;
        index |= tripwire.hasFace(BlockFace.NORTH) ? 1 << 3 : 0;
        index |= tripwire.isPowered() ? 1 << 4 : 0;
        index |= tripwire.hasFace(BlockFace.SOUTH) ? 1 << 5 : 0;
        index |= tripwire.hasFace(BlockFace.WEST) ? 1 << 6 : 0;
        return index;
    }

    @Override
    public int max()
    {
        return (int) Math.pow(2, 7);
    }

    @Nonnull
    @Override
    public Class<? extends BlockData> getBlockDataClass()
    {
        return Tripwire.class;
    }

    @Nonnull
    @Override
    public IBlockDataIndexer createNew(@Nonnull Material material)
    {
        return new TripwireBlockDataIndexer(material);
    }

    @Override
    @Nonnull
    BlockData createForIndex(int index)
    {
        Tripwire tripwire = (Tripwire) this.material.createBlockData();
        tripwire.setAttached((index >> 0 & 1) != 0);
        tripwire.setDisarmed((index >> 1 & 1) != 0);
        tripwire.setFace(BlockFace.EAST, (index >> 2 & 1) != 0);
        tripwire.setFace(BlockFace.NORTH, (index >> 3 & 1) != 0);
        tripwire.setPowered((index >> 4 & 1) != 0);
        tripwire.setFace(BlockFace.SOUTH, (index >> 5 & 1) != 0);
        tripwire.setFace(BlockFace.WEST, (index >> 6 & 1) != 0);
        return tripwire;
    }
}
