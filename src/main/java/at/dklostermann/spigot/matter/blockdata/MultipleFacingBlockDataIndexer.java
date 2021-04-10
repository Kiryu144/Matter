package at.dklostermann.spigot.matter.blockdata;

import at.dklostermann.spigot.matter.reflection.MaterialReflector;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class MultipleFacingBlockDataIndexer extends CachedBlockDataIndexer
{
    private final Material material;
    private final List<BlockFace> usedBlockFaces = new ArrayList<>();

    /**
     * This constructor creates an invalid state. To create a valid state, use createForIndex().
     */
    public MultipleFacingBlockDataIndexer()
    {
        this.material = null;
    }

    private MultipleFacingBlockDataIndexer(@Nonnull Material material)
    {
        this.material = material;
        Validate.isTrue(MaterialReflector.GetBlockData(material) == this.getBlockDataClass(),
                String.format("Invalid material %s for blockdata of type %s", material,
                        this.getBlockDataClass().getSimpleName()));
        MultipleFacing multipleFacingInstance = ((MultipleFacing) material.createBlockData());
        for (BlockFace blockFace : multipleFacingInstance.getAllowedFaces())
        {
            switch (blockFace)
            {
                case NORTH:
                case EAST:
                case SOUTH:
                case WEST:
                case UP:
                case DOWN: continue;
                default: throw new IllegalArgumentException(String.format("Material %s has allowed blockface outside" +
                        " the 6 cardinal directions. This is forbidden.", material));
            }
        }
        this.usedBlockFaces.addAll(multipleFacingInstance.getAllowedFaces());
    }

    @Override
    public int getIndex(@Nonnull BlockData blockData)
    {
        MultipleFacing multipleFacing = (MultipleFacing) blockData;
        int index = 0;
        for (int i = 0; i < this.usedBlockFaces.size(); ++i)
        {
            index |= multipleFacing.hasFace(this.usedBlockFaces.get(i)) ? 1 << i : 0;
        }
        return index;
    }

    @Override
    public int max()
    {
        return (int) Math.pow(2, this.usedBlockFaces.size());
    }

    @Nonnull
    @Override
    public Class<? extends BlockData> getBlockDataClass()
    {
        return MultipleFacing.class;
    }

    @Nonnull
    @Override
    public IBlockDataIndexer createNew(@Nonnull Material material)
    {
        return new MultipleFacingBlockDataIndexer(material);
    }

    @Nonnull
    @Override
    BlockData createForIndex(int index)
    {
        MultipleFacing multipleFacing = (MultipleFacing) this.material.createBlockData();
        for (int i = 0; i < this.usedBlockFaces.size(); ++i)
        {
            multipleFacing.setFace(this.usedBlockFaces.get(i), (index >> i & 1) != 0);
        }
        return multipleFacing;
    }
}
