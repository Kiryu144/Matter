package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

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

    public void place(@Nonnull Location location, @Nullable BlockFace blockFace, @Nullable Player player)
    {
        this.blockRepresentation.place(this, location, blockFace, player);
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
