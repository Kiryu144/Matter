package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.custom.block.representation.IBlockRepresentation;
import net.andrasia.spigot.core.custom.block.representation.RotatableBlockRepresentation;
import net.andrasia.spigot.core.custom.block.representation.SolidBlockRepresentation;
import net.andrasia.spigot.core.registry.CommonRegistry;
import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.blockdata.IBlockDataIndexer;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

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
        IBlockRepresentation blockRepresentation = value.getBlockRepresentation();
        this.register(blockRepresentation, value);
    }

    private void register(@Nonnull IBlockRepresentation blockRepresentation, @Nonnull CustomBlock customBlock)
    {
        if (blockRepresentation instanceof SolidBlockRepresentation)
        {
            SolidBlockRepresentation solidBlockRepresentation = (SolidBlockRepresentation) blockRepresentation;
            this.register(solidBlockRepresentation.getMaterial(), solidBlockRepresentation.getBlockDataIndexer(),
                    solidBlockRepresentation.getVariant(), customBlock);
        }
        else if (blockRepresentation instanceof RotatableBlockRepresentation)
        {
            RotatableBlockRepresentation rotatableBlockRepresentation = (RotatableBlockRepresentation) blockRepresentation;
            for (IBlockRepresentation iBlockRepresentation : rotatableBlockRepresentation.getBlockRepresentations())
            {
                this.register(iBlockRepresentation, customBlock);
            }
        }
    }

    private void register(@Nonnull Material material, @Nonnull IBlockDataIndexer blockDataIndexer, int variant, @Nonnull CustomBlock customBlock)
    {
        CustomBlock[] perBlockData = this.blockReferences[material.ordinal()];
        if (perBlockData == null)
        {
            perBlockData = new CustomBlock[blockDataIndexer.max()];
            this.blockReferences[material.ordinal()] = perBlockData;
        }
        perBlockData[variant] = customBlock;
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

    @Nullable
    public CustomBlock getCustomBlock(@Nonnull BlockData blockData)
    {
        IBlockDataIndexer indexer = Core.getInstance().getBlockDataIndexerRegistry().getIndexer(blockData.getMaterial());
        if (indexer == null)
        {
            return null;
        }
        return this.getCustomBlock(blockData.getMaterial(), indexer.getIndex(blockData));
    }

    @Override
    public void clear()
    {
        super.clear();
        Arrays.fill(this.blockReferences, null);
    }
}
