package at.dklostermann.spigot.matter.reflection;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    public static ItemMeta GetMeta(@NotNull ItemStack itemStack)
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

    public static void SetMeta(@NotNull ItemStack itemStack, ItemMeta meta)
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
