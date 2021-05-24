package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.CustomItemParseException;
import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CustomItemDurability extends CustomItem
{
    /**
     * This is the namespaced key, which stores the current custom durability name in the itemstack meta.
     */
    public static final NamespacedKey ITEM_DURABILITY_KEY = new NamespacedKey(Matter.getInstance(), "durability");

    private int durability = 64;

    public CustomItemDurability(@NotNull ConfigurationSection config, @NotNull String registryName, int registryIndex, short registryUUID)
    {
        super(config, registryName, registryIndex, registryUUID);

        try
        {
            this.durability = Math.max(1, config.getInt("durability"));
        }
        catch (Exception exception)
        {
            throw new CustomItemParseException(exception);
        }
    }

    public int getDurability()
    {
        return this.durability;
    }

    public CustomItemDurability setDurability(int durability)
    {
        this.durability = durability;
        return this;
    }

    protected void updateItemStackDamageDisplay(@Nonnull ItemMeta itemMeta, int damage, int vanillaDurability)
    {
        if (itemMeta instanceof Damageable)
        {
            Damageable damageable = (Damageable) itemMeta;
            int newDamage = (int) (((float) damage / this.durability) * vanillaDurability);
            damageable.setDamage(Math.min(vanillaDurability, newDamage));
        }
    }

    public void setItemStackDamage(@Nonnull ItemStack itemStack, int damage)
    {
        ItemMeta meta = itemStack.getItemMeta();
        meta.getPersistentDataContainer().set(ITEM_DURABILITY_KEY, PersistentDataType.INTEGER, damage);
        this.updateItemStackDamageDisplay(meta, damage, itemStack.getType().getMaxDurability());
        itemStack.setItemMeta(meta);
    }

    public int getItemStackDamage(@Nonnull ItemStack itemStack)
    {
        ItemMeta meta = itemStack.getItemMeta();
        Validate.notNull(meta);
        Integer damage = meta.getPersistentDataContainer().get(ITEM_DURABILITY_KEY, PersistentDataType.INTEGER);
        return damage != null ? damage : 0;
    }
}


















