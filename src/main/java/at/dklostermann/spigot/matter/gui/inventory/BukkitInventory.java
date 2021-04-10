package at.dklostermann.spigot.matter.gui.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BukkitInventory extends MatterInventory
{
    private final Inventory inventory;

    public BukkitInventory(@Nonnull InventoryType inventoryType)
    {
        this.inventory = Bukkit.createInventory(null, inventoryType);
    }

    public BukkitInventory(int slots)
    {
        this.inventory = Bukkit.createInventory(null, slots);
    }

    @Override
    public void setItem(int index, @Nullable ItemStack itemStack)
    {
        if (itemStack == null)
        {
            itemStack = new ItemStack(Material.AIR);
        }
        this.inventory.setItem(index, itemStack);
    }

    @Override
    public int getSize()
    {
        return this.inventory.getSize();
    }

    @Nonnull
    @Override
    public ItemStack getItem(int index)
    {
        ItemStack itemStack = this.inventory.getItem(index);
        return itemStack != null ? itemStack : new ItemStack(Material.AIR);
    }

    @Override
    public Inventory getInventory()
    {
        return this.inventory;
    }
}
