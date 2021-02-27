package net.andrasia.spigot.core.plugin;

import net.andrasia.spigot.core.config.JsonConfiguration;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.logging.Logger;

public abstract class CorePlugin extends JavaPlugin
{
    private final CoreLogger coreLogger = new CoreLogger(this);

    @Override
    public @NotNull Logger getLogger()
    {
        return this.coreLogger;
    }

    @Nonnull
    public FileConfiguration loadConfiguration(@Nonnull String filename) throws IOException, InvalidConfigurationException
    {
        FileConfiguration configuration = null;
        if (filename.endsWith(".yml") || filename.endsWith(".yaml"))
        {
            configuration = new YamlConfiguration();
        }
        else if (filename.endsWith(".json"))
        {
            configuration = new JsonConfiguration();
        }
        else
        {
            throw new IllegalArgumentException("Unable to determine configuration type using file ending.");
        }

        configuration.load(filename);
        return configuration;
    }
}
