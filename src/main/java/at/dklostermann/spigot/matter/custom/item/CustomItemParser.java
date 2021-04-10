package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.config.JsonConfiguration;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * CustomItem parser for ConfigurationSections. Can be used for example with YamlConfiguration or XJsonConfiguration.
 * @see org.bukkit.configuration.file.YamlConfiguration
 * @see JsonConfiguration
 */
public class CustomItemParser
{
    private final CustomItemRegistry customItemRegistry;
    private final Map<String, Class<? extends CustomItem>> customItemTypes = new HashMap<>();

    public CustomItemParser(CustomItemRegistry customItemRegistry)
    {
        this.customItemRegistry = customItemRegistry;
        this.registerCustomItemType("default", CustomItem.class);
    }

    public void registerCustomItemType(@Nonnull String name, Class<? extends CustomItem> clazz)
    {
        this.customItemTypes.put(name, clazz);
    }

    @Nullable
    private CustomItem createCustomItemFromType(@Nullable String type, @Nonnull String registryName,
                                                @Nonnull ConfigurationSection configurationSection) throws IllegalArgumentException
    {
        if (type == null)
        {
            return null;
        }

        Class<? extends CustomItem> clazz = this.customItemTypes.get(type);
        if (clazz == null)
        {
            throw new CustomItemParseException(String.format("Unknown class type '%s'", type));
        }
        return this.createCustomItemFromType(clazz, registryName, configurationSection);
    }

    @Nonnull
    private CustomItem createCustomItemFromType(@Nonnull Class<? extends CustomItem> clazz, @Nonnull String registryName,
                                                @Nonnull ConfigurationSection configurationSection) throws IllegalArgumentException
    {
        try
        {
            Constructor<? extends CustomItem> ctor = clazz.getConstructor(String.class, ConfigurationSection.class);
            return ctor.newInstance(registryName, configurationSection);
        }
        catch (CustomItemParseException e)
        {
            throw new CustomItemParseException(String.format("Unable to instantiate CustomItem %s:%s", clazz.getName(), e.getMessage()));
        }
        catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e)
        {
            throw new CustomItemParseException(String.format("Unable to instantiate CustomItem %s. Did you forget the required constructor?", clazz.getName()));
        }
    }

    /**
     * Parses items out of a configuration section. Any errors will be logged in the console.
     * @param configurationSection Section to parse the items out of.
     * @return Returns true if no errors occurred. False if at least one item failed to parse.
     */
    public boolean parseItems(@Nonnull ConfigurationSection configurationSection)
    {
        List<String> itemsParsed = new ArrayList<>();
        boolean success = true;
        for (String name : configurationSection.getKeys(false))
        {
            ConfigurationSection section = configurationSection.getConfigurationSection(name);
            if (section == null)
            {
                Matter.getInstance().getLogger().warning(String.format("Unable to parse custom item '%s' from config. Ill-formed config.", name));
                success = false;
                continue;
            }

            if (this.parseItem(name, section))
            {
                itemsParsed.add(name);
            }
            else
            {
                success = false;
            }
        }

        Matter.getInstance().getLogger().info("Custom items parsed: " + Arrays.toString(itemsParsed.toArray()));
        return success;
    }

    private boolean parseItem(@Nonnull String name, @Nonnull ConfigurationSection configurationSection)
    {
        String type = configurationSection.getString("type", "default");

        try
        {
            CustomItem customItem = this.createCustomItemFromType(type, name, configurationSection);
            if (customItem == null)
            {
                Matter.getInstance().getLogger().warning(String.format("Unable to instantiate custom item '%s'.", name));
                return false;
            }

            this.customItemRegistry.register(customItem);
            return true;
        }
        catch (CustomItemParseException exception)
        {
            Matter.getInstance().getLogger().warning(String.format("Unable to parse custom item '%s' from config: %s", name, exception.getMessage()));
            return false;
        }
    }
}
