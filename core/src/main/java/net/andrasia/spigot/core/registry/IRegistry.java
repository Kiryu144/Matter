package net.andrasia.spigot.core.registry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * In this context, a registry is basically a container with two keys (String, int) per value.
 * The String key can be choosen freely by the IRegistryEntry, using IRegistryEntry#getRegistryName().
 * The Integer key is practially an index, which is choosen by the IRegistry implementation. It is unique
 * and provides a way to access any entry ideally in a O(0) manner.
 */
public interface IRegistry<V extends IRegistryValue<V>>
{
    /**
     * Registers a value to the registry. Given value must be in a valid state for
     * getRegistryName() to return a proper, unique value.
     *
     * @param value The value to register.
     */
    void register(@Nonnull V value);

    /**
     * Fetched the value given the registry name. This returns the instance, not a copy.
     *
     * @param name Unique registry name
     * @return The value if found, otherwise null.
     */
    @Nullable
    V get(@Nonnull String name);

    /**
     * Fetches a value based on its index. The index is choosen by the IRegistry implementation and
     * can change over different instances of IRegistry. Don't rely on it!
     *
     * @param index The index of the value wanted
     * @return The value if found, otherwise null.
     */
    @Nullable
    V get(int index);

    /**
     * @return List of all values in order of registering them.
     */
    @Nonnull
    List<V> values();

    /**
     * @return List of all names in order of registering them.
     */
    @Nonnull
    List<String> names();

    /**
     * @return Returns a random value created in the constructor. Is used for identification.
     */
    short getInstanceID();

    /**
     * Resets the registry to its initial state.
     */
    void clear();
}
