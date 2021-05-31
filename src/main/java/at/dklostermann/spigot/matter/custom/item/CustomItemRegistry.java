package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.registry.Registry;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class CustomItemRegistry extends Registry<CustomItem>
{

    public boolean isCustomItem(@Nullable ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return false;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
        {
            return false;
        }

        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        Integer id = persistentDataContainer.get(CustomItem.ITEM_REG_INDEX, PersistentDataType.INTEGER);
        return id != null;
    }

    public void fixCustomItem(@Nullable ItemStack itemStack)
    {
        this.get(itemStack);
    }

    public void fixInventory(@Nullable Inventory inventory)
    {
        if(inventory == null)
        {
            return;
        }

        for (ItemStack itemStack : inventory.getContents())
        {
            this.fixCustomItem(itemStack);
        }
    }

    /**
     * This gets the custom item associated with a given itemstack.
     * The method also checks if the registry id is matching with the one stored in the itemstack.
     *
     * @param itemStack  ItemStack to check. Null is returned if itemstack is null.
     * @return Returns the customitem if detected.
     */
    @Nullable
    public CustomItem get(@Nullable ItemStack itemStack)
    {
        if (itemStack == null)
        {
            return null;
        }

        ItemMeta meta = itemStack.getItemMeta();
        if (meta == null)
        {
            return null;
        }

        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        Integer id = persistentDataContainer.get(CustomItem.ITEM_REG_INDEX, PersistentDataType.INTEGER);
        if (id == null)
        {
            return null;
        }

        final short registryUUID = CustomItem.GetUUID(id);
        if (registryUUID != this.getUUID())
        {
            String name = persistentDataContainer.get(CustomItem.ITEM_NAME_KEY, PersistentDataType.STRING);

            CustomItem customItem = this.get(name);
            short registryIndex = (short) customItem.getRegistryIndex();
            persistentDataContainer.set(CustomItem.ITEM_REG_INDEX, PersistentDataType.INTEGER, CustomItem.Combine(registryIndex, this.getUUID()));
            itemStack.setItemMeta(meta);
            return customItem;
        }

        return this.get(CustomItem.GetIndex(id));
    }
}
