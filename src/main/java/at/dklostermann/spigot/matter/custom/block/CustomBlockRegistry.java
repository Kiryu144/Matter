package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.block.representation.RotatableBlockRepresentation;
import at.dklostermann.spigot.matter.custom.block.representation.SolidBlockRepresentation;
import at.dklostermann.spigot.matter.registry.CommonRegistry;
import at.dklostermann.spigot.matter.blockdata.IBlockDataIndexer;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Function;

/**
 * This registry extension provides an easy way to find the CustomBlock for a Block.
 */
public class CustomBlockRegistry extends CommonRegistry<CustomBlock>
{
    private final CustomBlock[][] blockReferences = new CustomBlock[Material.values().length][];
    private Entity lastCorrelatingEntity = null; // TODO: This is very hacky, please find a better way soon.

    @Override
    protected void postRegister(@NotNull CustomBlock value)
    {
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
    public CustomBlock getCustomBlock(@Nullable Block block)
    {
        if (block == null)
        {
            return null;
        }

        if (block.getType().equals(Material.BARRIER))
        {
            for (Entity entity : block.getChunk().getEntities())
            {
                if (!entity.getType().equals(EntityType.ITEM_FRAME))
                {
                    continue;
                }

                ItemFrame itemFrame = (ItemFrame) entity;
                if (itemFrame.getCustomName() == null)
                {
                    continue;
                }

                Vector itemFrameLoc = itemFrame.getLocation().toVector();
                if (itemFrameLoc.getBlockX() != block.getX() || itemFrameLoc.getBlockZ() != block.getZ() || itemFrameLoc.getBlockY() != block.getY())
                {
                    continue;
                }

                this.lastCorrelatingEntity = itemFrame;
                return this.get(itemFrame.getCustomName());
            }
        }

        IBlockDataIndexer indexer = Matter.getInstance().getBlockDataIndexerRegistry().getIndexer(block.getType());
        if (indexer == null)
        {
            return null;
        }
        return this.getCustomBlock(block.getType(), indexer.getIndex(block.getBlockData()));
    }

    @Nullable
    public CustomBlock getCustomBlock(@Nonnull BlockData blockData)
    {
        IBlockDataIndexer indexer = Matter.getInstance().getBlockDataIndexerRegistry().getIndexer(blockData.getMaterial());
        if (indexer == null)
        {
            return null;
        }
        return this.getCustomBlock(blockData.getMaterial(), indexer.getIndex(blockData));
    }

    public Entity getLastCorrelatingEntity()
    {
        return this.lastCorrelatingEntity;
    }

    @Override
    public void clear()
    {
        super.clear();
        if (this.blockReferences != null)
        {
            Arrays.fill(this.blockReferences, null);
        }
        this.lastCorrelatingEntity = null;
    }
}
