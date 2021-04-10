package at.dklostermann.spigot.matter.registry;

import javax.annotation.Nonnull;

public interface IRegistryValue<V>
{
    /**
     * A unique identifier for this entry, if this entry is registered already it will return it's official registry name.
     * Otherwise it will return the name set in setRegistryName().
     * If neither are valid null is returned.
     *
     * @return Unique identifier or null if not ready to be registered.
     */
    @Nonnull
    String getRegistryName();

    /**
     * Returns the registry index set by setRegistryIndex().
     *
     * @see IRegistryValue#setRegistryIndex
     * @return Unique index for a given registry.
     */
    int getRegistryIndex();

    /**
     * A unique index for this entry. This MUST be called by the registry only, and only once.
     *
     * @see IRegistry
     * @param index Unique index for a given registry.
     */
    void setRegistryIndex(IRegistry<? extends IRegistryValue<V>> owningRegistry, int index);
}
