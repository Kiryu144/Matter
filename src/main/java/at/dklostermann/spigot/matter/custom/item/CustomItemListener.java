package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.custom.item.interaction.CustomItemBlockInteraction;
import at.dklostermann.spigot.matter.custom.item.interaction.CustomItemInteraction;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class CustomItemListener implements Listener
{
    private final CustomItemRegistry customItemRegistry;

    public CustomItemListener(@NotNull Plugin plugin, @NotNull CustomItemRegistry customItemRegistry)
    {
        this.customItemRegistry = customItemRegistry;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onPlayerInteractEvent(PlayerInteractEvent event)
    {
        CustomItem customItem = this.customItemRegistry.get(event.getItem());
        if (customItem != null)
        {
            boolean isAttack = event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK);
            CustomItemInteraction customItemInteraction = new CustomItemInteraction(event.getHand(), isAttack,
                    event.getItem(), customItem, event.getPlayer(), event.getClickedBlock(), event.getBlockFace());
            customItem.onInteract(customItemInteraction);
        }
    }

    @EventHandler
    private void onPlayerInteractEntityEvent(PlayerInteractEntityEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        ItemStack itemStack = event.getPlayer().getInventory().getItem(event.getHand());
        CustomItem customItem = this.customItemRegistry.get(itemStack);
        if (customItem != null)
        {
            CustomItemInteraction customItemInteraction = new CustomItemInteraction(event.getHand(), false, itemStack,
                    customItem, event.getPlayer(), event.getRightClicked());
            customItem.onInteract(customItemInteraction);
        }
    }

    @EventHandler
    private void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event)
    {
        if (event.isCancelled() || !(event.getDamager() instanceof LivingEntity))
        {
            return;
        }

        LivingEntity livingEntity = (LivingEntity) event.getDamager();
        ItemStack itemStack = livingEntity.getActiveItem();
        CustomItem customItem = this.customItemRegistry.get(itemStack);
        if (customItem != null)
        {
            CustomItemInteraction customItemInteraction = new CustomItemInteraction(EquipmentSlot.HAND, true,
                    itemStack, customItem, livingEntity, event.getEntity());
            customItem.onInteract(customItemInteraction);
        }
    }

    @EventHandler
    private void onBlockBreakEvent(BlockBreakEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = event.getPlayer().getInventory().getItem(event.getPlayer().getInventory().getHeldItemSlot());
        CustomItem customItem = this.customItemRegistry.get(itemStack);
        if (customItem != null)
        {
            CustomItemBlockInteraction customItemBlockInteraction = new CustomItemBlockInteraction(
                    CustomItemBlockInteraction.InteractionType.BREAK, EquipmentSlot.HAND, itemStack, customItem,
                    player, event.getBlock(), null);
            customItem.onBlockChange(customItemBlockInteraction);
        }
    }

    @EventHandler
    private void onBlockPlaceEvent(BlockPlaceEvent event)
    {
        if (event.isCancelled())
        {
            return;
        }

        Player player = event.getPlayer();
        ItemStack itemStack = event.getItemInHand();
        CustomItem customItem = this.customItemRegistry.get(itemStack);
        if (customItem != null)
        {
            CustomItemBlockInteraction customItemBlockInteraction = new CustomItemBlockInteraction(
                    CustomItemBlockInteraction.InteractionType.PLACE, event.getHand(), itemStack, customItem, player,
                    event.getBlock(), event.getBlock().getFace(event.getBlockAgainst()));
            customItem.onBlockChange(customItemBlockInteraction);
        }
    }

    @EventHandler
    private void onPlayerJoinEvent(PlayerJoinEvent event)
    {
        this.customItemRegistry.fixInventory(event.getPlayer().getInventory());
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event)
    {
        // TODO: Evaluate performance impact
        this.customItemRegistry.fixInventory(event.getInventory());
    }

    @EventHandler
    private void onItemPickupEvent(EntityPickupItemEvent event)
    {
        this.customItemRegistry.fixCustomItem(event.getItem().getItemStack());
    }
}












