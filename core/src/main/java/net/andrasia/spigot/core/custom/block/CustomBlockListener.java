package net.andrasia.spigot.core.custom.block;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomBlockListener implements Listener
{
    private final CustomBlockRegistry customBlockRegistry;

    public CustomBlockListener(@Nonnull Plugin plugin, @Nonnull CustomBlockRegistry customBlockRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBlock != null)
        {
            event.setDropItems(false);
            event.setExpToDrop(0);
            Location dropLocation = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
        }
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
