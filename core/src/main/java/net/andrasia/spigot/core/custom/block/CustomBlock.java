package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.registry.IRegistry;
import net.andrasia.spigot.core.registry.IRegistryValue;
import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.blockdata.IBlockDataIndexer;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class CustomBlock implements IRegistryValue<CustomBlock>
{
    private final String registryName;
    private final ConfigurationSection configurationSection;
    private final Material material;
    private final int materialIndex;
    private final List<ItemStack> drops = new ArrayList<>();
    private int registryIndex;

    public CustomBlock(@Nonnull String registryName, @Nonnull ConfigurationSection config) throws CustomBlockParseException
    {
        this.registryName = registryName;
        this.configurationSection = config;

        try
        {
            this.material =  Material.valueOf(this.configurationSection.getString("material", "null").toUpperCase());
            Validate.notNull(this.material);
            this.materialIndex = this.configurationSection.getInt("material_index");
        }
        catch (Exception exception)
        {
            throw new CustomBlockParseException(exception);
        }
    }

    /**
     * Should only be used by for debugging.
     */
    public CustomBlock(String registryName, Material material, int materialIndex)
    {
        this.registryName = registryName;
        this.material = material;
        this.materialIndex = materialIndex;
        this.configurationSection = null;
    }

    /**
     * This method is called sometimes after it has been registiered to its registry. This is so that any
     * cross-references like custom items had a chance to register.
     */
    public void initializeLater() throws CustomBlockParseException
    {
        if (this.configurationSection == null)
        {
            return;
        }

        try
        {
            for(String dropName : this.configurationSection.getStringList("drops"))
            {
                ItemStack drop = this.parseDrop(dropName);
                if (drop != null)
                {
                    this.drops.add(drop);
                }
            }
        }
        catch (Exception exception)
        {
            throw new CustomBlockParseException(exception);
        }
    }

    @Nullable
    private ItemStack parseDrop(@Nonnull String drop)
    {
        if (drop.startsWith("xitem"))
        {
            throw new NotImplementedException();
            /*
            String customItemName = drop.substring("xitem:".length());
            CustomItem customItem = Core.getInstance().getCustomItemRegistry().get(customItemName);
            if (customItem != null)
            {
                return customItem.createItemStack();
            }
            return null;*/
        }

        if (drop.startsWith("minecraft:"))
        {
            drop = drop.substring("minecraft:".length());
        }

        Material material = Material.valueOf(drop.toUpperCase());
        return new ItemStack(material);
    }

    public List<ItemStack> getDrops()
    {
        return this.drops;
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
