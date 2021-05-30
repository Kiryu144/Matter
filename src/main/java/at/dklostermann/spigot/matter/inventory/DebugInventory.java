package at.dklostermann.spigot.matter.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class DebugInventory extends SmartInventory
{
    private int ticksAlive = 0;
    private int closingAttempts = 0;

    public DebugInventory(@Nonnull Player viewer)
    {
        super(viewer, 3);
    }

    @Override
    public void onOpen()
    {
        this.getViewer().sendMessage("Opened DebugInventory");
    }

    @Override
    public void onTick()
    {
        ++this.ticksAlive;
    }

    @Override
    public boolean onClick(int slot, @NotNull ClickType clickType)
    {
        boolean blocked = slot % 2 == 0;
        this.getViewer().sendMessage(String.format("Clicked on slot %d in DebugInventory. Aborted=%b", slot, !blocked));
        return blocked;
    }

    @Override
    public boolean onClose(boolean forced)
    {
        if (this.closingAttempts++ < 3)
        {
            this.getViewer().sendMessage("Try again!");
            return false;
        }
        else
        {
            this.getViewer().sendMessage("U got it after " + this.ticksAlive + " ticks");
            return true;
        }
    }
}
