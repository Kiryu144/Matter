package net.andrasia.spigot.core.custom.block;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
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

        if (event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
        {
            return;
        }

        CustomBlock customBLock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBLock != null)
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

        CustomBlock customBLock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBLock != null)
        {
            event.setCancelled(true);
        }
    }
}
