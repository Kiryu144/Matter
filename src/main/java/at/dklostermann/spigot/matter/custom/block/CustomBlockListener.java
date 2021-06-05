package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.custom.block.representation.ItemFrameBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.CustomItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CustomBlockListener implements Listener
{
    private final CustomBlockRegistry customBlockRegistry;
    private final CustomItemRegistry customItemRegistry;

    public CustomBlockListener(@NotNull Plugin plugin, @NotNull CustomBlockRegistry customBlockRegistry, @NotNull CustomItemRegistry customItemRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
        this.customItemRegistry = customItemRegistry;
        Bukkit.getPluginManager().registerEvents(this, plugin);
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

        if (!customBlock.onPlayerBreak(event.getPlayer(), event.getBlock()))
        {
            event.setCancelled(true);
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
            customBlock.getDrops().forEach(itemStack -> dropLocation.getWorld().dropItemNaturally(dropLocation, itemStack));
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
        /*
        if (event.getCurrentItem() != null && !event.getCurrentItem().getType().isAir() && event.getRawSlot() >= 0 && event.getRawSlot() < 9)
        {
            return;
        }

        HumanEntity player = event.getWhoClicked();
        Block block = player.getTargetBlock(5);
        CustomBlock customBlock = this.customBlockRegistry.getCustomBlock(block);
        if (customBlock == null)
        {
            return;
        }

        event.setCancelled(true);
        ItemStack naturalDrop = customBlock.getNaturalDrop();

        PlayerInventory inventory = player.getInventory();
        int slotToUse = -1;
        for (int i = 0; i < 9; ++i)
        {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType().isAir())
            {
                if (slotToUse < 0)
                {
                    slotToUse = i;
                }
            }
            else
            {
                CustomItem customItem = this.customItemRegistry.get(itemStack);
                if (customItem == customBlockItem)
                {
                    inventory.setHeldItemSlot(i);
                    return;
                }
            }
        }

        ItemStack currentlyHeld = inventory.getItem(inventory.getHeldItemSlot());
        if (currentlyHeld == null || currentlyHeld.getType().isAir())
        {
            slotToUse = inventory.getHeldItemSlot();
        }
        else if (slotToUse == -1)
        {
            slotToUse = inventory.getHeldItemSlot();
        }

        inventory.setItem(slotToUse, customBlockItem.createItemStack());
        inventory.setHeldItemSlot(slotToUse);*/
    }
}
