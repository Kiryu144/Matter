package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.custom.block.representation.ItemFrameBlockRepresentation;
import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.custom.item.CustomItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

public class CustomBlockListener implements Listener
{
    private CustomBlockRegistry customBlockRegistry;
    private CustomItemRegistry customItemRegistry;

    public CustomBlockListener(@Nonnull Plugin plugin)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void setCustomBlockRegistry(CustomBlockRegistry customBlockRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
    }

    public void setCustomItemRegistry(CustomItemRegistry customItemRegistry)
    {
        this.customItemRegistry = customItemRegistry;
    }

    @EventHandler
    private void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBlock == null)
        {
            return;
        }

        if (customBlock.getBlockRepresentation() instanceof ItemFrameBlockRepresentation)
        {
            this.customBlockRegistry.getLastCorrelatingEntity().remove();
        }

        if (!event.getPlayer().getGameMode().equals(GameMode.CREATIVE))
        {
            event.setDropItems(false);
            event.setExpToDrop(0);
            Location dropLocation = event.getBlock().getLocation().add(0.5, 0.5, 0.5);
        }
    }

    @EventHandler
    private void onBlockInteractEvent(PlayerInteractEvent event)
    {
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK) ||
                !event.getPlayer().getGameMode().equals(GameMode.CREATIVE) ||
                !event.getClickedBlock().getType().equals(Material.BARRIER) ||
                !event.getHand().equals(EquipmentSlot.HAND) ||
                event.getItem() != null)
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getClickedBlock());
        if (customBlock == null || !(customBlock.getBlockRepresentation() instanceof ItemFrameBlockRepresentation))
        {
            return;
        }

        ItemFrame itemFrame = (ItemFrame) this.customBlockRegistry.getLastCorrelatingEntity();
        itemFrame.setRotation(itemFrame.getRotation().rotateClockwise());
    }

    @EventHandler
    private void onBlockPhysicsEvent(BlockPhysicsEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(event.getBlock());
        if (customBlock != null)
        {
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onInventoryCreativeEvent(InventoryCreativeEvent event)
    {
        if (event.getCurrentItem() != null && !event.getCurrentItem().getType().isAir())
        {
            return;
        }

        Block block = event.getWhoClicked().getTargetBlock(5);
        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(block);
        if (customBlock == null)
        {
            return;
        }

        event.setCancelled(true);
        CustomItem customBlockItem = customBlock.getCustomItem();

        int firstEmpty = -1;
        for (int i = 0; i < 9; ++i)
        {
            ItemStack itemStack = event.getWhoClicked().getInventory().getItem(i);
            if (itemStack == null || itemStack.getType().isAir())
            {
                if (firstEmpty < 0)
                {
                    firstEmpty = i;
                }
            }
            else
            {
                CustomItem customItem = this.customItemRegistry.get(itemStack, false);
                if (customItem == customBlockItem)
                {
                    event.getWhoClicked().getInventory().setHeldItemSlot(i);
                    return;
                }
            }
        }

        if (firstEmpty == -1)
        {
            firstEmpty = event.getWhoClicked().getInventory().getHeldItemSlot();
        }

        event.getWhoClicked().getInventory().setItem(firstEmpty, customBlockItem.createItemStack());
    }
}
