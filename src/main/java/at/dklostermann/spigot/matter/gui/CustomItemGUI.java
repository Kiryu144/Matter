package at.dklostermann.spigot.matter.gui;

import at.dklostermann.spigot.matter.custom.item.CustomItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomItemGUI
{
    private final List<Inventory> pages = new ArrayList<>();
    private final List<Integer> blacklistedSlots = Arrays.asList(36, 37, 45, 46, 43, 44, 52, 53);
    private final int prevPageSlot = 45;
    private final int nextPageSlot = 53;

    public CustomItemGUI()
    {
        this.addPage();
    }

    private void addPage()
    {
        Inventory inventory = Bukkit.createInventory(null, 54, "Custom Item GUI");
        inventory.setItem(this.prevPageSlot, new ItemStack(Material.CYAN_STAINED_GLASS_PANE));
        inventory.setItem(this.nextPageSlot, new ItemStack(Material.CYAN_STAINED_GLASS_PANE));
        this.pages.add(inventory);
    }

    @Nonnull
    public Inventory getFirstPage()
    {
        return this.pages.get(0);
    }

    public void add(@Nonnull CustomItem customItem)
    {
        int slot = -1;
        Inventory inventory = this.pages.get(0);
        for (int i = 0; i < inventory.getSize(); ++i)
        {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType().isAir())
            {
                if (!this.blacklistedSlots.contains(i))
                {
                    slot = i;
                    break;
                }
            }
        }

        if (slot < 0)
        {
            this.addPage();
            this.add(customItem);
        }
        else
        {
            inventory.setItem(slot, customItem.createItemStack());
        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onInventoryClick(InventoryClickEvent event)
    {
        Inventory inventory = null;
        for (Inventory page : this.pages)
        {
            if (event.getView().getTopInventory().equals(page))
            {
                inventory = page;
                break;
            }
        }

        if (inventory == null)
        {
            return;
        }

        event.setCancelled(true);
    }
}



















