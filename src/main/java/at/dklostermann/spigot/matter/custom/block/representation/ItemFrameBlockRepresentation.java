package at.dklostermann.spigot.matter.custom.block.representation;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemFrameBlockRepresentation implements IBlockRepresentation
{
    private final ItemStack itemStack;

    public ItemFrameBlockRepresentation(@Nonnull ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    public void place(@Nonnull CustomBlock customBlock, @Nonnull Location location, @javax.annotation.Nullable BlockFace blockFace, @Nullable Player player)
    {
        location.getWorld().spawnEntity(location, EntityType.ITEM_FRAME, CreatureSpawnEvent.SpawnReason.DEFAULT, entity -> {
            ItemFrame itemFrame = (ItemFrame) entity;
            itemFrame.setFacingDirection(blockFace != null ? blockFace : BlockFace.DOWN);
            itemFrame.setItem(this.itemStack.clone(), false);
            itemFrame.setCustomName(customBlock.getRegistryName());
            itemFrame.setFixed(true);
            itemFrame.setInvulnerable(true);
            itemFrame.setVisible(false);
            itemFrame.setCustomNameVisible(false);
        });
        location.getBlock().setType(Material.BARRIER);
    }
}
