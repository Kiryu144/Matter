package net.andrasia.spigot.core.blockdata;

import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.reflection.MaterialReflector;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MaterialBlockDataIndexerRegistry
{
    private final IBlockDataIndexer[] indexerMap = new IBlockDataIndexer[Material.values().length];
    private final ArrayList<Material> knownMaterials = new ArrayList<>();
    private final List<String> knownMaterialNames = new ArrayList<>();

    public void register(@Nonnull IBlockDataIndexer blockDataIndexer)
    {
        for (Material material : Material.values())
        {
            if (!material.isBlock())
            {
                continue;
            }

            // This is to prevent a bug mentioned in SPIGOT-6349
            // This is already fixed in spigot, but not in paper.
            // To be sure we should wait till at least 1.17 is released before we remove this check.
            if (material.equals(Material.MOSSY_COBBLESTONE))
            {
                continue;
            }

            Class<? extends BlockData> blockData = MaterialReflector.GetBlockData(material);
            if (blockData != null && blockData.equals(blockDataIndexer.getBlockDataClass()))
            {
                this.indexerMap[material.ordinal()] = blockDataIndexer.createNew(material);
                this.knownMaterials.add(material);
                this.knownMaterialNames.add(material.toString());
                Core.getInstance().getLogger().info(String.format("Registered BlockDataIndexer %s for %s", blockData.getSimpleName(), material.toString()));
            }
        }
    }

    public ArrayList<Material> getKnownMaterials()
    {
        return this.knownMaterials;
    }

    @Nonnull
    public List<String> getKnownMaterialNames()
    {
        return this.knownMaterialNames;
    }

    /**
     * This operation is almost O(0) (very fast)
     * @param material Material for which the indexer is needed.
     * @return Returns indexer if found, else null.
     */
    @Nullable
    public IBlockDataIndexer getIndexer(@Nonnull Material material)
    {
        return this.indexerMap[material.ordinal()];
    }
}
