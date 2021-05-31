package at.dklostermann.spigot.matter.inventory;

import at.dklostermann.spigot.matter.Matter;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SmartInventoryManager implements Listener
{
    private final Map<Inventory, SmartInventory> inventorySmartInventoryMap = new HashMap<>();

    public SmartInventoryManager(@NotNull Plugin plugin)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void open(@NotNull SmartInventory smartInventory)
    {
        this.inventorySmartInventoryMap.remove(smartInventory.getViewer().getOpenInventory().getTopInventory());
        this.inventorySmartInventoryMap.put(smartInventory.asSpigot(), smartInventory);
        smartInventory.getViewer().openInventory(smartInventory.asSpigot());
        smartInventory.onOpen();
    }

    public void tick()
    {
        this.inventorySmartInventoryMap.forEach((itemStacks, smartInventory) -> smartInventory.onTick());
    }

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent event)
    {
        if (event.getView().getTopInventory() != event.getClickedInventory())
        {
            return;
        }

        SmartInventory smartInventory = this.inventorySmartInventoryMap.get(event.getView().getTopInventory());
        if (smartInventory != null && event.getSlot() >= 0 && event.getSlot() < smartInventory.asSpigot().getSize())
        {
            if (!smartInventory.onClick(event.getSlot(), event.getClick()))
            {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onInventoryDragEvent(InventoryDragEvent event)
    {
        if (event.getView().getTopInventory() != event.getInventory())
        {
            return;
        }

        SmartInventory smartInventory = this.inventorySmartInventoryMap.get(event.getView().getTopInventory());
        if (smartInventory != null)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInventoryCloseEvent(InventoryCloseEvent event)
    {
        SmartInventory smartInventory = this.inventorySmartInventoryMap.get(event.getView().getTopInventory());
        if (smartInventory != null)
        {
            if (!smartInventory.onClose(false))
            {
                final HumanEntity player = event.getPlayer();
                Bukkit.getScheduler().scheduleSyncDelayedTask(Matter.getInstance(), () -> player.openInventory(smartInventory.asSpigot()));
            }
            else
            {
                this.inventorySmartInventoryMap.remove(smartInventory.asSpigot());
            }
        }
    }

    @EventHandler
    private void onPlayerQuitEvent(PlayerQuitEvent event)
    {
        SmartInventory smartInventory = this.inventorySmartInventoryMap.get(event.getPlayer().getOpenInventory().getTopInventory());
        if (smartInventory != null)
        {
            smartInventory.onClose(true);
            this.inventorySmartInventoryMap.remove(smartInventory.asSpigot());
        }
    }
}























