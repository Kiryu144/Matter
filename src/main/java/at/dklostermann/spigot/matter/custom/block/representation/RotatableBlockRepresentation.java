package at.dklostermann.spigot.matter.custom.block.representation;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RotatableBlockRepresentation implements IBlockRepresentation
{
    private static final int[][] ROTATION_LOOKUP =
            {
                    // North, East, South, West, Up, Down
                    { 0, 0, 0, 0, 0, 0 }, // 0
                    { 0, 0, 0, 0, 0, 0 }, // 1
                    { 0, 1, 0, 1, 0, 0 }, // 2
                    { 0, 1, 0, 1, 2, 2 }, // 3
                    { 0, 1, 2, 3, 0, 0 }, // 4
                    { 0, 0, 0, 0, 0, 0 }, // 5
                    { 0, 1, 2, 3, 4, 5 }, // 6
            };
    private final List<IBlockRepresentation> blockRepresentations = new ArrayList<>();
    private final List<BlockData> blockDataList = new ArrayList<>();

    public RotatableBlockRepresentation(@NotNull List<IBlockRepresentation> blockRepresentations)
    {
        this.blockRepresentations.addAll(blockRepresentations);
        Validate.isTrue(this.blockRepresentations.size() > 0);

        this.blockRepresentations.forEach(iBlockRepresentation -> this.blockDataList.addAll(iBlockRepresentation.getBlockDatas()));
    }

    public List<IBlockRepresentation> getBlockRepresentations()
    {
        return this.blockRepresentations;
    }

    @Override
    public void place(@NotNull CustomBlock customBlock, @NotNull Location location, @Nullable BlockFace blockFace, @Nullable Player player)
    {
        if (player != null && this.blockRepresentations.size() <= 4)
        {
            blockFace = player.getFacing();
        }
        else if (blockFace == null)
        {
            blockFace = BlockFace.NORTH;
        }

        int representationToUse = ROTATION_LOOKUP[this.blockRepresentations.size()][blockFace.ordinal()];
        this.blockRepresentations.get(representationToUse < this.blockRepresentations.size() ? representationToUse : 0).place(customBlock, location, blockFace, player);
    }

    @Override
    public @NotNull List<BlockData> getBlockDatas()
    {
        return this.blockDataList;
    }
}
