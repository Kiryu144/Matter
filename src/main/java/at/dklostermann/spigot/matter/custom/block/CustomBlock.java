package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.custom.CustomGameObject;
import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomBlock extends CustomGameObject
{
    private final IBlockRepresentation blockRepresentation;
    private final CustomItem customItem;

    public CustomBlock(@NotNull String registryName, int registryIndex, short registryUUID, @Nonnull IBlockRepresentation blockRepresentation, @Nullable CustomItem customItem)
    {
        super(registryName, registryIndex, registryUUID);

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
}
