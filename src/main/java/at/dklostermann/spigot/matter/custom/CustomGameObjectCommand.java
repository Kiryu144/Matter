package at.dklostermann.spigot.matter.custom;

import at.dklostermann.spigot.matter.registry.IRegistry;
import co.aikar.commands.BaseCommand;

public abstract class CustomGameObjectCommand<T extends CustomGameObject> extends BaseCommand
{
    private IRegistry<T> registry;

    public CustomGameObjectCommand(IRegistry<T> registry)
    {
        this.registry = registry;
    }

    public IRegistry<T> getRegistry()
    {
        return this.registry;
    }

    public void setRegistry(IRegistry<T> registry)
    {
        this.registry = registry;
    }
}
