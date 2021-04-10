package at.dklostermann.spigot.matter.gui.inventory;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomInventory extends BukkitInventory
{
    private final int[] slots;

    public CustomInventory(@NotNull InventoryType inventoryType, @Nonnull List<Integer> slots)
    {
        super(inventoryType);
        this.slots = slots.stream().mapToInt(i->i).toArray();
    }

    public CustomInventory(int size, @Nonnull List<Integer> slots)
    {
        super(size);
        this.slots = slots.stream().mapToInt(i->i).toArray();
    }

    public int getSlot(int slot)
    {
        return this.slots[slot];
    }

    @Override
    public void setItem(int index, @Nullable ItemStack itemStack)
    {
        super.setItem(this.getSlot(index), itemStack);
    }

    @Override
    public int getSize()
    {
        return this.slots.length;
    }

    @NotNull
    @Override
    public ItemStack getItem(int index)
    {
        return super.getItem(this.getSlot(index));
    }
}
