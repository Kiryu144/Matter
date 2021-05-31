package at.dklostermann.spigot.matter.config;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.Nullable;

public interface IConfigParseable
{
    void parseFromConfig(@Nullable ConfigurationSection configurationSection);
}
