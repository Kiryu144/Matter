package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.custom.block.representation.IBlockRepresentation;
import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.registry.IRegistry;
import net.andrasia.spigot.core.registry.IRegistryValue;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomBlock implements IRegistryValue<CustomBlock>
{
    private final String registryName;
    private final IBlockRepresentation blockRepresentation;
    private final CustomItem customItem;

    private int registryIndex;

    public CustomBlock(String registryName, IBlockRepresentation blockRepresentation, CustomItem customItem)
    {
        this.registryName = registryName;
        this.blockRepresentation = blockRepresentation;
        this.customItem = customItem;
    }

    public void place(@Nonnull Location location, @Nullable BlockFace blockFace)
    {
        this.blockRepresentation.place(location, blockFace);
    }

    public IBlockRepresentation getBlockRepresentation()
    {
        return this.blockRepresentation;
    }

    public CustomItem getCustomItem()
    {
        return this.customItem;
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
