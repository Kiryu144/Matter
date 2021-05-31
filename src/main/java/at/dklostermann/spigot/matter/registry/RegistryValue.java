package at.dklostermann.spigot.matter.registry;

import org.jetbrains.annotations.NotNull;

public class RegistryValue implements IRegistryValue
{
    private final String registryName;
    private final int registryIndex;
    private final short registryUUID;

    public RegistryValue(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex)
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
