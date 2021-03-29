package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.custom.block.representation.ItemFrameBlockRepresentation;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomBlockListener implements Listener
{
    private CustomBlockRegistry customBlockRegistry;

    public CustomBlockListener(@Nonnull Plugin plugin, @Nonnull CustomBlockRegistry customBlockRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void setCustomBlockRegistry(CustomBlockRegistry customBlockRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
    }

    @EventHandler
    private void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBlock == null)
        {
            return;
        }

        if (customBlock.getBlockRepresentation() instanceof ItemFrameBlockRepresentation)
        {
            this.customBlockRegistry.getLastCorrelatingEntity().remove();
        }

        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
        {
            event.setDropItems(false);
            event.setExpToDrop(0);
            Location dropLocation = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
        }
    }

    @EventHandler
    private void onBlockInteractEvent(PlayerInteractEvent event)
    {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
                !event.getPlayer().getGameMode().equals(GameMode.CREATIVE) ||
                !event.getClickedBlock().getType().equals(Material.BARRIER) ||
                !event.getHand().equals(EquipmentSlot.HAND) ||
                event.getItem() != null)
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getClickedBlock());
        if (customBlock == null || !(customBlock.getBlockRepresentation() instanceof ItemFrameBlockRepresentation))
        {
            return;
        }

        ItemFrame itemFrame = (ItemFrame) this.customBlockRegistry.getLastCorrelatingEntity();
        itemFrame.setRotation(itemFrame.getRotation().rotateClockwise());
    }

    @EventHandler
    private void onBlockPhysicsEvent(BlockPhysicsEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBlock != null)
        {
            event.setCancelled(true);
        }
    }
}
