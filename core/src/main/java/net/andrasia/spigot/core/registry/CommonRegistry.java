package net.andrasia.spigot.core.registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class CommonRegistry<V extends IRegistryValue<V>> implements IRegistry<V>
{
    private final Map<String, V> names = new HashMap<>();
    private final List<V> valueList = new ArrayList<>();
    private final List<String> nameList = new ArrayList<>();

    private short instanceID;

    public CommonRegistry()
    {
        this.instanceID = (short) new Random().nextInt(Short.MAX_VALUE);
    }

    @Override
    public void register(@Nonnull V value)
    {
        final String registryName = value.getRegistryName();
        if (this.names.containsKey(registryName))
        {
            throw new IllegalStateException("Value with registry name was already registered.");
        }
        this.names.put(registryName, value);
        this.valueList.add(value);
        this.nameList.add(registryName);
        value.setRegistryIndex(this, this.valueList.size() - 1);
    }

    @Override
    @Nullable
    public V get(@Nonnull String name)
    {
        return this.names.get(name);
    }

    @Nullable
    @Override
    public V get(int index)
    {
        if (index < 0 || index >= this.valueList.size())
        {
            return null;
        }

        return this.valueList.get(index);
    }

    @Override
    @Nonnull
    public List<V> values()
    {
        return this.valueList;
    }

    @Override
    @Nonnull
    public List<String> names()
    {
        return this.nameList;
    }

    @Override
    public short getInstanceID()
    {
        return this.instanceID;
    }

    @Override
    public void clear()
    {
        this.names.clear();
        this.valueList.clear();
        this.nameList.clear();
        this.instanceID = (short) new Random().nextInt(Short.MAX_VALUE);
    }
}
