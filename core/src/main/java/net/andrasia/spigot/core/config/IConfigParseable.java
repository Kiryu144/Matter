package net.andrasia.spigot.core.config;

import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nullable;

public interface IConfigParseable
{
    void parseFromConfig(@Nullable ConfigurationSection configurationSection);
}
