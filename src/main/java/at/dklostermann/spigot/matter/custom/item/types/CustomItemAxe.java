package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.item.CustomItemParseException;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class CustomItemAxe extends CustomItemDurability
{
    public CustomItemAxe(@NotNull String registryName, @NotNull ConfigurationSection data) throws CustomItemParseException
    {
        super(registryName, data);
    }

    public CustomItemAxe(@NotNull String registryName)
    {
        super(registryName);
    }
}
