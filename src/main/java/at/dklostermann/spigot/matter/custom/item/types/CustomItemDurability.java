package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class CustomItemDurability extends CustomItem
{
    /**
     * This is the namespaced key, which stores the current custom durability name in the itemstack meta.
     */
    public static final NamespacedKey ITEM_DURABILITY_KEY = new NamespacedKey(Matter.getInstance(), "durability");

    private int durability = 64;

    public CustomItemDurability(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex)
    {
        super(registry, registryName, registryIndex);
    }

    public int getDurability()
    {
        return this.durability;
    }

    public void setDurability(int durability)
    {
        this.durability = durability;
    }

    protected void updateItemStackDamageDisplay(@NotNull ItemMeta itemMeta, int damage, int vanillaDurability)
    {
        if (itemMeta instanceof Damageable)
        {
            Damageable damageable = (Damageable) itemMeta;
            int newDamage = (int) (((float) damage / this.durability) * vanillaDurability);
            damageable.setDamage(Math.min(vanillaDurability, newDamage));
        }
    }

    public void setItemStackDamage(@NotNull ItemStack itemStack, int damage)
    {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(ITEM_DURABILITY_KEY, PersistentDataType.INTEGER, damage);
        this.updateItemStackDamageDisplay(meta, damage, itemStack.getType().getMaxDurability());
        itemStack.setItemMeta(meta);
    }

    public int getItemStackDamage(@NotNull ItemStack itemStack)
    {
        ItemMeta meta = itemStack.getItemMeta();
        Validate.notNull(meta);
        Integer damage = meta.getPersistentDataContainer().get(ITEM_DURABILITY_KEY, PersistentDataType.INTEGER);
        return damage != null ? damage : 0;
    }
}


















