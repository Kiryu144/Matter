package net.andrasia.spigot.core.custom.item.types;

import net.andrasia.spigot.core.custom.item.CustomItemParseException;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class CustomItemPickaxe extends CustomItemDurability
{
    public CustomItemPickaxe(@NotNull String registryName, @NotNull ConfigurationSection data) throws CustomItemParseException
    {
        super(registryName, data);
    }

    public CustomItemPickaxe(@NotNull String registryName)
    {
        super(registryName);
    }
}
