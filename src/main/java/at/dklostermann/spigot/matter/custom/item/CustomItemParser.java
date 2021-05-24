package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.custom.CustomGameObjectParser;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;


public class CustomItemParser extends CustomGameObjectParser
{
    private final CustomItemRegistry customItemRegistry;

    public CustomItemParser(CustomItemRegistry customItemRegistry)
    {
        super("items");
        this.customItemRegistry = customItemRegistry;
    }

    public void parse(@Nonnull String name, @Nonnull ConfigurationSection configurationSection)
    {
        this.customItemRegistry.register(integer -> new CustomItem(configurationSection, name, integer, this.customItemRegistry.getUUID()));
    }
}
