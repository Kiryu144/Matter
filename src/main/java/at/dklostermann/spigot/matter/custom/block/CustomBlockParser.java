package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.custom.CustomGameObjectParser;
import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItemRegistry;
import at.dklostermann.spigot.matter.custom.item.types.CustomItemBlock;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;

public class CustomBlockParser extends CustomGameObjectParser
{
    private final CustomBlockRegistry customBlockRegistry;
    private final CustomItemRegistry customItemRegistry;

    public CustomBlockParser(@Nonnull CustomBlockRegistry customBlockRegistry, @Nonnull CustomItemRegistry customItemRegistry)
    {
        super("blocks");
        this.customBlockRegistry = customBlockRegistry;
        this.customItemRegistry = customItemRegistry;
    }

    @Override
    public void parse(@Nonnull String name, @Nonnull ConfigurationSection configurationSection)
    {
        IBlockRepresentation blockRepresentation = IBlockRepresentation.FromConfig(configurationSection);

        CustomItemBlock customItem = (CustomItemBlock) this.customItemRegistry.register(integer -> new CustomItemBlock(configurationSection.getConfigurationSection("item"), name, integer, this.customItemRegistry.getUUID()));
        CustomBlock customBlock = this.customBlockRegistry.register(integer -> new CustomBlock(name, integer, this.customBlockRegistry.getUUID(), blockRepresentation, customItem));

        customItem.setCustomBlock(customBlock);
    }
}
