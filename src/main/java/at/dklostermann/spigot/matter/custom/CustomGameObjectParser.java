package at.dklostermann.spigot.matter.custom;

import at.dklostermann.spigot.matter.Matter;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;

public abstract class CustomGameObjectParser
{
    private final String configName;

    protected CustomGameObjectParser(@Nonnull String configName)
    {
        this.configName = configName;
    }

    public abstract void parse(@Nonnull String name, @Nonnull ConfigurationSection configurationSection);

    public void parseAll(@Nonnull ConfigurationSection configurationSection)
    {
        for (String name : configurationSection.getKeys(false))
        {
            ConfigurationSection section = configurationSection.getConfigurationSection(name);
            if (section == null)
            {
                Matter.getInstance().getLogger().warning(String.format("Unable to parse custom object '%s' from config. Ill-formed config.", name));
                continue;
            }

            try
            {
                this.parse(name, section);
            }
            catch (Exception exception)
            {
                Matter.getInstance().getLogger().warning(String.format("Unable to parse custom object '%s' from config", name));
                exception.printStackTrace();
            }
        }
    }

    @Nonnull
    public String getConfigName()
    {
        return this.configName;
    }
}
