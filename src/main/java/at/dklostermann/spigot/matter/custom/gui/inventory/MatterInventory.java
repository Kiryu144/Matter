package at.dklostermann.spigot.matter.custom.gui.inventory;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class MatterInventory
{
    public abstract void setItem(int index, @Nullable ItemStack itemStack);
    public abstract int getSize();
    @Nonnull
    public abstract ItemStack getItem(int index);
    public abstract Inventory getInventory();
    public abstract void rename(@Nonnull String title);

    public void clear()
    {
        for (int i = 0; i < this.getSize(); ++i)
        {
            this.setItem(i, new ItemStack(Material.AIR));
        }
    }
}
