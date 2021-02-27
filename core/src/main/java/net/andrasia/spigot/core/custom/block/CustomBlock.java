package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.blockdata.IBlockDataIndexer;
import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.registry.IRegistry;
import net.andrasia.spigot.core.registry.IRegistryValue;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomBlock implements IRegistryValue<CustomBlock>
{
    private final String registryName;
    private Material material;
    private int materialIndex;
    private int registryIndex;
    private CustomItem item = null;

    public CustomBlock(@Nonnull String registryName, @Nonnull ConfigurationSection configurationSection) throws CustomBlockParseException
    {
        this.registryName = registryName;

        try
        {
            this.material =  Material.valueOf(configurationSection.getString("material", "null").toUpperCase());
            Validate.notNull(this.material);
            this.materialIndex = configurationSection.getInt("material_index");
        }
        catch (Exception exception)
        {
            throw new CustomBlockParseException(exception);
        }
    }

    public CustomBlock(@Nonnull String registryName)
    {
        this.registryName = registryName;
    }

    public void place(@Nonnull Location location)
    {
        IBlockDataIndexer blockDataIndexer = this.getBlockDataIndexer();
        if (blockDataIndexer == null)
        {
            throw new IllegalArgumentException("No BlockDataIndexer is registered for given material");
        }
        BlockData blockData = blockDataIndexer.fromIndex(this.materialIndex);
        location.getBlock().setType(this.material, false);
        location.getBlock().setBlockData(blockData, false);
    }

    public CustomBlock setMaterial(@Nonnull Material material, int materialIndex)
    {
        this.material = material;
        this.materialIndex = materialIndex;
        return this;
    }

    public CustomBlock setItem(CustomItem item)
    {
        this.item = item;
        return this;
    }

    public CustomItem getItem()
    {
        return this.item;
    }

    public int getMaterialIndex()
    {
        return this.materialIndex;
    }

    @Nonnull
    public Material getMaterial()
    {
        return this.material;
    }

    public int[] getMaterialIndicies()
    {
        return new int[] { this.materialIndex };
    }

    @Nonnull
    public BlockData getDefaultBlockData()
    {
        return this.getBlockDataIndexer().fromIndex(this.getMaterialIndicies()[0]);
    }

    @Nullable
    public IBlockDataIndexer getBlockDataIndexer()
    {
        return Core.getInstance().getBlockDataIndexerRegistry().getIndexer(this.material);
    }

    @Override
    @Nonnull
    public String getRegistryName()
    {
        return this.registryName;
    }

    @Override
    public int getRegistryIndex()
    {
        return this.registryIndex;
    }

    @Override
    public void setRegistryIndex(IRegistry<? extends IRegistryValue<CustomBlock>> owningRegistry, int index)
    {
        this.registryIndex = index;
    }
}
