package at.dklostermann.spigot.matter.custom;

import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CustomGameObject implements IRegistryValue
{
    private final String registryName;
    private final int registryIndex;
    private final short registryUUID;

    public CustomGameObject(@Nonnull String registryName, int registryIndex, short registryUUID)
    {
        this.registryName = registryName;
        this.registryIndex = registryIndex;
        this.registryUUID = registryUUID;
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
