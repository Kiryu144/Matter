package at.dklostermann.spigot.matter.registry;

import javax.annotation.Nonnull;

public interface IRegistryValue
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
     * Returns the registry index.
     *
     * @return Unique index for a given registry.
     */
    int getRegistryIndex();

    /**
     * Returns the registry UUID it is registered to.
     *
     * @return registry uuid.
     */
    short getRegistryUUID();
}
