package at.dklostermann.spigot.matter.registry;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Function;

/**
 * In this context, a registry is basically a container with two keys (String, int) per value.
 * The String key can be choosen freely by the IRegistryEntry, using IRegistryEntry#getRegistryName().
 * The Integer key is practially an index, which is choosen by the IRegistry implementation. It is unique
 * and provides a way to access any entry ideally in a O(0) manner.
 */
public interface IRegistry<V extends IRegistryValue>
{
    /**
     * Registers a value to the registry.
     *
     * @param function Function that gives the new registry index
     */
    <T extends V> T register(Function<Integer, T> function);

    /**
     * Fetched the value given the registry name. This returns the instance, not a copy.
     *
     * @param name Unique registry name
     * @return The value if found, otherwise null.
     */
    @Nullable
    V get(@NotNull String name);

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
    @NotNull
    List<V> values();

    /**
     * @return List of all names in order of registering them.
     */
    @NotNull
    List<String> names();

    /**
     * @return Returns a random value created in the constructor. Is used for identification.
     */
    short getUUID();

    /**
     * Resets the registry to its initial state.
     */
    void clear();
}
