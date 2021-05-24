package at.dklostermann.spigot.matter.custom.gui.button;

import at.dklostermann.spigot.matter.custom.gui.InventoryGui;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class GuiGiveItemButton implements IGuiButton
{
    private final ItemStack displayItemStack;
    private final ItemStack itemStack;

    public GuiGiveItemButton(ItemStack displayItemStack, ItemStack itemStack)
    {
        this.displayItemStack = displayItemStack;
        this.itemStack = itemStack;
    }

    public GuiGiveItemButton(ItemStack itemStack)
    {
        this.itemStack = itemStack;
        this.displayItemStack = itemStack;
    }

    @Override
    public boolean onInteract(@NotNull InventoryGui page, @NotNull Player player, @NotNull ClickType clickType)
    {
        player.getInventory().addItem(this.itemStack);
        player.playSound(player.getEyeLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 3.0f, 1.5f);
        return false;
    }

    @NotNull
    @Override
    public ItemStack getItemStack()
    {
        return this.displayItemStack;
    }

    @Override
    public boolean needsUpdate()
    {
        return false;
    }
}
