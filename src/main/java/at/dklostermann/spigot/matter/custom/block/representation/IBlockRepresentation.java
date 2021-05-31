package at.dklostermann.spigot.matter.custom.block.representation;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public interface IBlockRepresentation
{
    void place(@Nonnull CustomBlock customBlock, @Nonnull Location location, @Nullable BlockFace blockFace, @Nullable Player player);
}
