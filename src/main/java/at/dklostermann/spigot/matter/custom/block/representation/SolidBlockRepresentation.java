package at.dklostermann.spigot.matter.custom.block.representation;

import at.dklostermann.spigot.matter.blockdata.IBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.MaterialBlockDataIndexerRegistry;
import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class SolidBlockRepresentation implements IBlockRepresentation
{
    private final Material material;
    private final int variant;
    private final BlockData blockData;
    private final IBlockDataIndexer blockDataIndexer;

    public SolidBlockRepresentation(@NotNull MaterialBlockDataIndexerRegistry blockDataIndexerRegistry, @NotNull Material material, int variant)
    {
        this.material = material;
        this.variant = variant;

        Validate.isTrue(this.material.isBlock());
        this.blockDataIndexer = blockDataIndexerRegistry.getIndexer(material);
        Validate.notNull(this.blockDataIndexer);

        this.blockData = this.blockDataIndexer.fromIndex(variant);
    }

    @Override
    public void place(@NotNull CustomBlock customBlock, @NotNull Location location, @javax.annotation.Nullable BlockFace blockFace, @Nullable Player player)
    {
        location.getBlock().setBlockData(this.blockData, false);
    }

    @Override
    public @NotNull List<BlockData> getBlockDatas()
    {
        return Collections.singletonList(this.blockData);
    }

    public Material getMaterial()
    {
        return this.material;
    }

    public int getVariant()
    {
        return this.variant;
    }

    public IBlockDataIndexer getBlockDataIndexer()
    {
        return this.blockDataIndexer;
    }
}
