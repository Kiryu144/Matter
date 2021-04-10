package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.custom.block.representation.IBlockRepresentation;
import at.dklostermann.spigot.matter.custom.item.CustomItemRegistry;
import at.dklostermann.spigot.matter.custom.item.types.CustomItemBlock;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomBlockParser
{
    private final CustomBlockRegistry customBlockRegistry;
    private final CustomItemRegistry customItemRegistry;

    public CustomBlockParser(@Nonnull CustomBlockRegistry customBlockRegistry, @Nonnull CustomItemRegistry customItemRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
        this.customItemRegistry = customItemRegistry;
    }

    /**
     * Parses blocks out of a configuration section. Any errors will be logged in the console.
     *
     * @param configurationSection Section to parse the blocks out of.
     * @return Returns true if no errors occurred. False if at least one block failed to parse.
     */
    public boolean parseBlocks(@Nonnull ConfigurationSection configurationSection)
    {
        List<String> blockParsed = new ArrayList<>();
        boolean success = true;
        for (String name : configurationSection.getKeys(false))
        {
            ConfigurationSection section = configurationSection.getConfigurationSection(name);
            if (section == null)
            {
                Matter.getInstance().getLogger().warning(String.format("Unable to parse custom block '%s' from config. Ill-formed config.", name));
                success = false;
                continue;
            }

            if (this.parseBlock(name, section))
            {
                blockParsed.add(name);
            }
            else
            {
                success = false;
            }
        }

        Matter.getInstance().getLogger().info("Custom blocks parsed: " + Arrays.toString(blockParsed.toArray()));
        return success;
    }

    private boolean parseBlock(@Nonnull String name, @Nonnull ConfigurationSection configurationSection)
    {
        try
        {
            CustomItemBlock customItem = new CustomItemBlock(name, configurationSection.getConfigurationSection("item"));

            IBlockRepresentation blockRepresentation = IBlockRepresentation.FromConfig(configurationSection);
            CustomBlock customBlock = new CustomBlock(name, blockRepresentation, customItem);
            this.customBlockRegistry.register(customBlock);

            this.customItemRegistry.register(customItem);

            customItem.setCustomBlock(customBlock);
        }
        catch (Exception exception)
        {
            Matter.getInstance().getLogger().warning(String.format("Unable to parse custom item '%s' from config: %s", name, exception.getMessage()));
            exception.printStackTrace();
            return false;
        }
        return true;
    }
}
