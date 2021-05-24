package at.dklostermann.spigot.matter.registry;

import org.apache.commons.lang.Validate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.function.Function;

public class CommonRegistry<V extends IRegistryValue> implements IRegistry<V>
{
    private short uuid = (short) (Math.random() * Short.MAX_VALUE);
    private final Map<String, V> names = new HashMap<>();
    private final List<V> valueList = new ArrayList<>();
    private final List<String> nameList = new ArrayList<>();

    public CommonRegistry()
    {
        this.clear();
    }

    @Override
    public V register(Function<Integer, V> function)
    {
        final int index = this.valueList.size();
        final V value = function.apply(index);

        Validate.notNull(value);

        final String registryName = value.getRegistryName();
        if (this.names.containsKey(registryName))
        {
            throw new IllegalStateException("Value with registry name was already registered.");
        }

        this.names.put(registryName, value);
        this.valueList.add(value);
        this.nameList.add(registryName);

        this.postRegister(value);

        return value;
    }

    protected void postRegister(@Nonnull V value)
    {

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
    public short getUUID()
    {
        return this.uuid;
    }

    @Override
    public void clear()
    {
        this.names.clear();
        this.valueList.clear();
        this.nameList.clear();
        this.uuid = (short) (Math.random() * Short.MAX_VALUE);
    }
}
