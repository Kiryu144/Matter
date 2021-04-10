package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.registry.CommonRegistry;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nullable;

public class CustomItemRegistry extends CommonRegistry<CustomItem>
{
    /**
     * Combines two shorts into an integer.
     *
     * @param index left short
     * @param id    right short
     * @return combined integer
     */
    public static int Combine(short index, short id)
    {
        return (index << 16) | (id & 0xFFFF);
    }

    /**
     * @param i Combined integer
     * @return left short
     * @see CustomItemRegistry#Combine
     */
    public static short GetIndex(int i)
    {
        return (short) (i >>> 16);
    }

    /**
     * @param i Combined integer
     * @return right short
     * @see CustomItemRegistry#Combine
     */
    public static short GetID(int i)
    {
        return (short) (i & 0xFFFF);
    }

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
        this.get(itemStack, true);
    }

    public void fixInventory(@Nullable Inventory inventory)
    {
        if(inventory == null)
        {
            return;
        }

        for (ItemStack itemStack : inventory.getContents())
        {
            try
            {
                this.fixCustomItem(itemStack);
            }
            catch (Exception ignored) { }
        }
    }

    /**
     * This gets the custom item associated with a given itemstack.
     * The method also checks if the registry id is matching with the one stored in the itemstack.
     *
     * @param itemStack  ItemStack to check. Null is returned if itemstack is null.
     * @param checkIndex If true fixes the registryIndex & registryID of the itemstack. Otherwise will match using
     *                   the registryName which is way slower.
     * @return Returns the customitem if detected.
     */
    @Nullable
    public CustomItem get(@Nullable ItemStack itemStack, boolean checkIndex)
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

        short registryID = GetID(id);
        if (registryID != this.getInstanceID())
        {
            String name = persistentDataContainer.get(CustomItem.ITEM_NAME_KEY, PersistentDataType.STRING);
            if (!checkIndex)
            {
                return this.get(name);
            }

            CustomItem customItem = this.get(name);
            short registryIndex = (short) customItem.getRegistryIndex();
            persistentDataContainer.set(CustomItem.ITEM_REG_INDEX, PersistentDataType.INTEGER, Combine(registryIndex, this.getInstanceID()));
            itemStack.setItemMeta(meta);
            return customItem;
        }

        return this.get(GetIndex(id));
    }
}
