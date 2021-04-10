package at.dklostermann.spigot.matter.gui;

import at.dklostermann.spigot.matter.gui.inventory.MatterInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class InventoryGui
{
    private final MatterInventory matterInventory;
    private final IGuiButton[] guiButtons;

    public InventoryGui(@Nonnull MatterInventory matterInventory)
    {
        this.matterInventory = matterInventory;
        this.guiButtons = new IGuiButton[matterInventory.getSize()];
    }

    public void setButton(int slot, @Nullable IGuiButton button)
    {
        this.guiButtons[slot] = button;
    }

    @Nullable
    public IGuiButton getButton(int slot)
    {
        return this.guiButtons[slot];
    }

    public MatterInventory getMatterInventory()
    {
        return this.matterInventory;
    }
}
