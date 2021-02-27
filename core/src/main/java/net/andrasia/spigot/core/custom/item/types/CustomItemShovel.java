package net.andrasia.spigot.core.custom.item.types;

import net.andrasia.spigot.core.custom.item.CustomItemParseException;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class CustomItemShovel extends CustomItemDurability
{
    public CustomItemShovel(@NotNull String registryName, @NotNull ConfigurationSection data) throws CustomItemParseException
    {
        super(registryName, data);
    }

    public CustomItemShovel(@NotNull String registryName)
    {
        super(registryName);
    }
}
