package at.dklostermann.spigot.matter.gui;

import at.dklostermann.spigot.matter.gui.button.IGuiButton;
import at.dklostermann.spigot.matter.gui.inventory.CustomInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class InventoryGuiListener implements Listener
{
    private final Map<Inventory, InventoryGui> inventoryGuiMap = new HashMap<>();

    public InventoryGuiListener(@Nonnull Plugin plugin)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void register(@Nonnull InventoryGui inventoryGui)
    {
        // TODO: There is a memory "leak", as unused inventories never get removed. fix that.
        this.inventoryGuiMap.put(inventoryGui.getMatterInventory().getInventory(), inventoryGui);
    }

    @Nullable
    public InventoryGui get(@Nullable Inventory inventory)
    {
        return this.inventoryGuiMap.get(inventory);
    }

    public void replace(@Nonnull Inventory from, @Nonnull Inventory to)
    {
        this.inventoryGuiMap.put(to, this.inventoryGuiMap.get(from));
    }

    @EventHandler
    private void onInventoryClickEvent(InventoryClickEvent event)
    {
        if (event.getView().getTopInventory() != event.getClickedInventory())
        {
            return;
        }

        InventoryGui inventoryGui = this.get(event.getView().getTopInventory());
        if (inventoryGui == null)
        {
            System.out.println("yayeet");
            return;
        }

        int slot = event.getRawSlot();
        if (slot < 0 || slot == 999)
        {
            return;
        }

        if (inventoryGui.getMatterInventory() instanceof CustomInventory)
        {
            slot = ((CustomInventory) inventoryGui.getMatterInventory()).convertRealToCustom(slot);
        }

        IGuiButton button = inventoryGui.getButton(slot);
        try
        {
            if (button == null || !button.onInteract(inventoryGui, (Player) event.getWhoClicked(), event.getClick()))
            {
                event.setCancelled(true);
                if (inventoryGui.isDone())
                {
                    event.getWhoClicked().closeInventory();
                    this.inventoryGuiMap.remove(inventoryGui.getMatterInventory().getInventory());
                }
            }
        }
        catch (Exception exception)
        {
            event.setCancelled(true);
            throw exception;
        }
    }

    @EventHandler
    private void onInventoryDragEvent(InventoryDragEvent event)
    {
        InventoryGui inventoryGui = this.get(event.getView().getTopInventory());
        if (inventoryGui != null)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInventoryCloseEvent(InventoryCloseEvent event)
    {
        if (event.getView().getTopInventory() != event.getInventory())
        {
            return;
        }
        // TODO Implement
    }
}



















