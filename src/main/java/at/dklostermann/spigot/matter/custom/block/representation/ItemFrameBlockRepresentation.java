package at.dklostermann.spigot.matter.custom.block.representation;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ItemFrameBlockRepresentation implements IBlockRepresentation
{
    private final ItemStack itemStack;

    public ItemFrameBlockRepresentation(@NotNull ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    public void place(@NotNull CustomBlock customBlock, @NotNull Location location, @javax.annotation.Nullable BlockFace blockFace, @Nullable Player player)
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

    @Override
    public @NotNull List<BlockData> getBlockDatas()
    {
        return new ArrayList<>();
    }
}
