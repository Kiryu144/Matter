package net.andrasia.spigot.core.custom.block.representation;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public interface IBlockRepresentation
{
    void place(@Nonnull Location location, @Nullable BlockFace blockFacePlacedOn);

    @Nonnull
    static IBlockRepresentation FromConfig(@Nonnull ConfigurationSection configurationSection)
    {
        String type = configurationSection.getString("type");
        Validate.notNull(type);

        switch (type.toLowerCase(Locale.ROOT))
        {
            case "solid": return SolidBlockRepresentation.FromConfig(configurationSection);
            case "rotatable": return RotatableBlockRepresentation.FromConfig(configurationSection);
            default: throw new IllegalArgumentException("Unknown type.");
        }
    }
}
