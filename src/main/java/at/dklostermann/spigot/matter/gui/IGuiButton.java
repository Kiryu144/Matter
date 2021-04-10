package at.dklostermann.spigot.matter.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;

public interface IGuiButton
{
    boolean onInteract(@Nonnull InventoryGui page, @Nonnull Player player, @Nonnull ClickType clickType);

    @Nonnull
    ItemStack getItemStack();
}
