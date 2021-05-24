package at.dklostermann.spigot.matter.custom;

import at.dklostermann.spigot.matter.registry.IRegistry;
import org.bukkit.event.Listener;

public abstract class CustomGameObjectListener<V extends CustomGameObject> implements Listener
{
    private IRegistry<V> registry;

    public CustomGameObjectListener(IRegistry<V> registry)
    {
        this.registry = registry;
    }

    public IRegistry<V> getRegistry()
    {
        return this.registry;
    }

    public void setRegistry(IRegistry<V> registry)
    {
        this.registry = registry;
    }
}
