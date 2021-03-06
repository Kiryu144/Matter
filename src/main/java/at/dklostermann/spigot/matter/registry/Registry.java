package at.dklostermann.spigot.matter.registry;

import org.apache.commons.lang.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

public class Registry<V extends IRegistryValue> implements IRegistry<V>
{
    private short uuid = (short) (Math.random() * Short.MAX_VALUE);
    private final Map<String, V> names = new HashMap<>();
    private final List<V> valueList = new ArrayList<>();
    private final List<String> nameList = new ArrayList<>();
    private final List<Consumer<V>> onRegisterCallbacks = new ArrayList<>();

    public Registry()
    {
        this.clear();
    }

    @Override
    public <T extends V> T register(Function<Integer, T> function)
    {
        final int index = this.valueList.size();
        final T value = function.apply(index);

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

        this.onRegisterCallbacks.forEach(vConsumer -> vConsumer.accept(value));

        return value;
    }

    protected void postRegister(@NotNull V value)
    {

    }

    public void onRegister(@NotNull Consumer<V> callback)
    {
        this.onRegisterCallbacks.add(callback);
    }

    @Override
    @Nullable
    public V get(@NotNull String name)
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
    @NotNull
    public List<V> values()
    {
        return this.valueList;
    }

    @Override
    @NotNull
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
