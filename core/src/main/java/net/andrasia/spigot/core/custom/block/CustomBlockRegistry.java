package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.registry.CommonRegistry;
import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.blockdata.IBlockDataIndexer;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This registry extension provides an easy way to find the CustomBlock for a Block.
 */
public class CustomBlockRegistry extends CommonRegistry<CustomBlock>
{
    private final CustomBlock[][] blockReferences = new CustomBlock[Material.values().length][];

    @Override
    public void register(@Nonnull CustomBlock value)
    {
        super.register(value);
        int matOrdinal = value.getMaterial().ordinal();
        CustomBlock[] perBlockData = this.blockReferences[matOrdinal];
        if (perBlockData == null)
        {
            Validate.notNull(value.getBlockDataIndexer(), "BlockDataIndexer is not found for material " + value.getMaterial().toString());
            perBlockData = new CustomBlock[value.getBlockDataIndexer().max()];
            this.blockReferences[matOrdinal] = perBlockData;
        }

        for (int index : value.getMaterialIndicies())
        {
            perBlockData[index] = value;
        }
    }

    @Nullable
    public CustomBlock getCustomBlock(@Nonnull Material material, int blockDataID)
    {
        CustomBlock[] perBlockData = this.blockReferences[material.ordinal()];
        return perBlockData != null ? perBlockData[blockDataID] : null;
    }

    @Nullable
    public CustomBlock getCustomBlock(@Nonnull Block block)
    {
        IBlockDataIndexer indexer = Core.getInstance().getBlockDataIndexerRegistry().getIndexer(block.getType());
        if (indexer == null)
        {
            return null;
        }
        return this.getCustomBlock(block.getType(), indexer.getIndex(block.getBlockData()));
    }
}
