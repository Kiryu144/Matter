package at.dklostermann.spigot.matter.registry;

import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class RegistryValue implements IRegistryValue
{
    private final String registryName;
    private final int registryIndex;
    private final short registryUUID;

    public RegistryValue(@Nonnull IRegistry<? extends IRegistryValue> registry, @Nonnull String registryName, int registryIndex)
    {
        this.registryName = registryName;
        this.registryIndex = registryIndex;
        this.registryUUID = registry.getUUID();
    }

    @NotNull
    @Override
    public String getRegistryName()
    {
        return this.registryName;
    }

    @Override
    public int getRegistryIndex()
    {
        return this.registryIndex;
    }

    @Override
    public short getRegistryUUID()
    {
        return this.registryUUID;
    }
}
