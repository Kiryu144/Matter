package at.dklostermann.spigot.matter.custom.gui.button;

import at.dklostermann.spigot.matter.custom.gui.InventoryGui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class GuiLambdaButton implements IGuiButton
{
    private final ItemStack itemStack;
    private final Consumer<InteractData> consumer;

    public GuiLambdaButton(@Nonnull ItemStack itemStack, @Nonnull Consumer<InteractData> consumer)
    {
        this.itemStack = itemStack;
        this.consumer = consumer;
    }

    @Override
    public boolean onInteract(@NotNull InventoryGui page, @NotNull Player player, @NotNull ClickType clickType)
    {
        this.consumer.accept(new InteractData(page, player, clickType));
        return false;
    }

    @Override
    public boolean needsUpdate()
    {
        return false;
    }

    @NotNull
    @Override
    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    public static class InteractData
    {
        private final InventoryGui inventoryGui;
        private final Player player;
        private final ClickType clickType;

        public InteractData(InventoryGui inventoryGui, Player player, ClickType clickType)
        {
            this.inventoryGui = inventoryGui;
            this.player = player;
            this.clickType = clickType;
        }

        public InventoryGui getInventoryGui()
        {
            return this.inventoryGui;
        }

        public Player getPlayer()
        {
            return this.player;
        }

        public ClickType getClickType()
        {
            return this.clickType;
        }
    }
}
