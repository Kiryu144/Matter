package net.andrasia.spigot.core.reflection;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;

public class ItemStackReflector
{
    private static Field metaField = null;

    public static Field GetMetaField()
    {
        if (metaField == null)
        {
            try
            {
                metaField = ItemStack.class.getDeclaredField("meta");
                metaField.setAccessible(true);
            }
            catch (NoSuchFieldException e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return metaField;
    }

    @Nullable
    public static ItemMeta GetMeta(@Nonnull ItemStack itemStack)
    {
        try
        {
            return (ItemMeta) GetMetaField().get(itemStack);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
            return null;
        }
    }

    public static void SetMeta(@Nonnull ItemStack itemStack, ItemMeta meta)
    {
        try
        {
            GetMetaField().set(itemStack, meta);
        }
        catch (IllegalAccessException e)
        {
            e.printStackTrace();
        }
    }
}
