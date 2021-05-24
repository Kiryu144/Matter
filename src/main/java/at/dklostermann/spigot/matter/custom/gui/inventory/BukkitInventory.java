package at.dklostermann.spigot.matter.custom.gui.inventory;

import at.dklostermann.spigot.matter.Matter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class BukkitInventory extends MatterInventory
{
    private Inventory inventory;

    public BukkitInventory(@Nonnull InventoryType inventoryType, @Nonnull String title)
    {
        this.inventory = Bukkit.createInventory(null, inventoryType, title);
    }

    public BukkitInventory(int slots, @Nonnull String title)
    {
        this.inventory = Bukkit.createInventory(null, slots, title);
    }

    @Override
    public void rename(@Nonnull String title)
    {
        Inventory newInventory = Bukkit.createInventory(null, this.inventory.getSize(), title);
        ItemStack[] contents = this.inventory.getContents();
        newInventory.setContents(contents);
        Matter.getInstance().getInventoryGuiListener().replace(this.inventory, newInventory);

        for (HumanEntity humanEntity : this.inventory.getViewers().toArray(new HumanEntity[0]))
        {
            humanEntity.openInventory(newInventory);
        }

        this.inventory = newInventory;
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
