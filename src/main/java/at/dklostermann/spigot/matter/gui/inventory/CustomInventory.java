package at.dklostermann.spigot.matter.gui.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomInventory extends BukkitInventory
{
    private final int[] slotsRealToCustom;
    private final int[] slotsCustomToReal;

    public CustomInventory(@NotNull InventoryType inventoryType, @Nonnull String title, @Nonnull List<Integer> slots)
    {
        super(inventoryType, title);
        this.slotsRealToCustom = new int[this.getInventory().getSize()];
        this.slotsCustomToReal = slots.stream().mapToInt(i->i).toArray();

        for (int i = 0; i < this.slotsRealToCustom.length; ++i)
        {
            this.slotsRealToCustom[i] = slots.indexOf(i);
        }
    }

    public CustomInventory(int size, @Nonnull String title, @Nonnull List<Integer> slots)
    {
        super(size, title);
        this.slotsRealToCustom = new int[this.getInventory().getSize()];
        this.slotsCustomToReal = slots.stream().mapToInt(i->i).toArray();

        for (int i = 0; i < this.slotsRealToCustom.length; ++i)
        {
            this.slotsRealToCustom[i] = slots.indexOf(i);
        }
    }

    public int convertRealToCustom(int slot)
    {
        return this.slotsRealToCustom[slot];
    }

    @Override
    public void setItem(int index, @Nullable ItemStack itemStack)
    {
        super.setItem(this.slotsCustomToReal[index], itemStack);
    }

    @Override
    public int getSize()
    {
        return this.slotsRealToCustom.length;
    }

    @NotNull
    @Override
    public ItemStack getItem(int index)
    {
        return super.getItem(this.slotsCustomToReal[index]);
    }
}
