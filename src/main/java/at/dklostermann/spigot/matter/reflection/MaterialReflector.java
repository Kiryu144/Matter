package at.dklostermann.spigot.matter.reflection;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

public class MaterialReflector
{
    private static Class<? extends BlockData>[] blockDataPerMaterial;

    /**
     * @note We cant use Material#createBlockData(), as it returns the vanilla equivalent to the spigot wrapper.
     */
    @Nullable
    public static Class<? extends BlockData> GetBlockData(@NotNull Material material)
    {

        if (blockDataPerMaterial == null)
        {
            blockDataPerMaterial = new Class[Material.values().length];
            try
            {
                Field blockDataField = Material.class.getField("data");
                for (Material m : Material.values())
                {
                    //noinspection unchecked
                    blockDataPerMaterial[m.ordinal()] = (Class<? extends BlockData>) blockDataField.get(m);
                }
            }
            catch (NoSuchFieldException | IllegalAccessException | ClassCastException e)
            {
                e.printStackTrace();
                Bukkit.getServer().shutdown();
            }
        }
        return blockDataPerMaterial[material.ordinal()];
    }
}
