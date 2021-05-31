package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.types.CustomItemBlock;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import at.dklostermann.spigot.matter.registry.RegistryValue;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CustomBlock extends RegistryValue
{
    private final IBlockRepresentation blockRepresentation;
    private CustomItemBlock customItem;

    public CustomBlock(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex, @NotNull IBlockRepresentation blockRepresentation)
    {
        super(registry, registryName, registryIndex);
        this.blockRepresentation = blockRepresentation;
    }

    public void setCustomItem(@NotNull CustomItemBlock customItem)
    {
        this.customItem = customItem;
    }

    public void place(@NotNull Location location, @Nullable BlockFace blockFace, @Nullable Player player)
    {
        this.blockRepresentation.place(this, location, blockFace, player);
    }

    @NotNull
    public IBlockRepresentation getBlockRepresentation()
    {
        return this.blockRepresentation;
    }

    @Nullable
    public CustomItem getCustomItem()
    {
        return this.customItem;
    }
}
