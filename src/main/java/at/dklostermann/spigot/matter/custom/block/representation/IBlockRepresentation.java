package at.dklostermann.spigot.matter.custom.block.representation;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface IBlockRepresentation
{
    void place(@NotNull CustomBlock customBlock, @NotNull Location location, @Nullable BlockFace blockFace, @Nullable Player player);
    @NotNull List<BlockData> getBlockDatas();
}
