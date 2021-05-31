package at.dklostermann.spigot.matter.inventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class SmartInventory
{
    private final Player viewer;
    private final Inventory inventory;

    public SmartInventory(@NotNull Player viewer, @NotNull InventoryType inventoryType)
    {
        this.viewer = viewer;
        this.inventory = Bukkit.createInventory(null, inventoryType);
    }

    public SmartInventory(@NotNull Player viewer, int rows)
    {
        this.viewer = viewer;
        this.inventory = Bukkit.createInventory(null, rows*9);
    }

    public void onOpen()
    {

    }

    public void onTick()
    {

    }

    public boolean onClick(int slot, @NotNull ClickType clickType)
    {
        return false;
    }

    public boolean onClose(boolean forced)
    {
        return true;
    }

    public Inventory asSpigot()
    {
        return this.inventory;
    }

    public Player getViewer()
    {
        return this.viewer;
    }
}
