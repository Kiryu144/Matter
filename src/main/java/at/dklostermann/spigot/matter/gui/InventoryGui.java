package at.dklostermann.spigot.matter.gui;

import at.dklostermann.spigot.matter.gui.button.IGuiButton;
import at.dklostermann.spigot.matter.gui.inventory.MatterInventory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventoryGui
{
    private final MatterInventory matterInventory;
    private final IGuiButton[] guiButtons;
    private boolean done = false;

    public InventoryGui(@Nonnull MatterInventory matterInventory)
    {
        this.matterInventory = matterInventory;
        this.guiButtons = new IGuiButton[matterInventory.getSize()];
    }

    public void setButton(int slot, @Nullable IGuiButton button)
    {
        this.guiButtons[slot] = button;
        this.updateButton(slot);
    }

    @Nullable
    public IGuiButton getButton(int slot)
    {
        if (slot < 0 || slot > this.guiButtons.length)
        {
            return null;
        }
        return this.guiButtons[slot];
    }

    public void updateButton(int slot)
    {
        IGuiButton button = this.guiButtons[slot];
        this.matterInventory.setItem(slot, button == null ? new ItemStack(Material.AIR) : button.getItemStack());
    }

    protected void done()
    {
        this.done = true;
    }

    public boolean isDone()
    {
        return this.done;
    }

    public MatterInventory getMatterInventory()
    {
        return this.matterInventory;
    }
}
