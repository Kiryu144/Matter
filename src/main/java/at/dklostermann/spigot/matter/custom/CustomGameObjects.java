package at.dklostermann.spigot.matter.custom;

import at.dklostermann.spigot.matter.registry.IRegistry;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomGameObjects<T extends CustomGameObject>
{
    private final IRegistry<T> registry;
    private final Listener listener;
    private final CustomGameObjectCommand<T> commands;
    private final CustomGameObjectParser parser;

    public CustomGameObjects(@Nonnull IRegistry<T> registry, @Nullable Listener listener, @Nullable CustomGameObjectCommand<T> commands, @Nullable CustomGameObjectParser parser)
    {
        this.registry = registry;
        this.listener = listener;
        this.commands = commands;
        this.parser = parser;
    }

    @Nonnull
    public IRegistry<T> getRegistry()
    {
        return this.registry;
    }

    @Nullable
    public Listener getListener()
    {
        return this.listener;
    }

    @Nullable
    public CustomGameObjectCommand<T> getCommands()
    {
        return this.commands;
    }

    @Nullable
    public CustomGameObjectParser getParser()
    {
        return this.parser;
    }
}
