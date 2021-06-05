package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.types.CustomItemBlock;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import at.dklostermann.spigot.matter.registry.RegistryValue;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomBlock extends RegistryValue
{
    private final IBlockRepresentation blockRepresentation;
    private ItemStack naturalDrop = null;

    public CustomBlock(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex, @NotNull IBlockRepresentation blockRepresentation)
    {
        super(registry, registryName, registryIndex);
        this.blockRepresentation = blockRepresentation;
    }

    public void place(@NotNull Location location, @Nullable BlockFace blockFace, @Nullable Player player)
    {
        this.blockRepresentation.place(this, location, blockFace, player);
    }

    public boolean onPlayerBreak(@NotNull Player player, @NotNull Block block)
    {
        return true;
    }

    public ItemStack getNaturalDrop()
    {
        return this.naturalDrop;
    }

    public void setNaturalDrop(@Nullable ItemStack naturalDrop)
    {
        this.naturalDrop = naturalDrop;
    }

    @NotNull
    public List<ItemStack> getDrops()
    {
        if (this.naturalDrop != null)
        {
            return Collections.singletonList(this.naturalDrop);
        }
        else
        {
            return new ArrayList<>();
        }
    }

    @NotNull
    public IBlockRepresentation getBlockRepresentation()
    {
        return this.blockRepresentation;
    }
}
