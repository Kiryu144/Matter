package net.andrasia.spigot.core.custom.block.representation;

import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class RotatableBlockRepresentation implements IBlockRepresentation
{
    private final List<IBlockRepresentation> blockRepresentations = new ArrayList<>();

    public RotatableBlockRepresentation(@Nonnull List<IBlockRepresentation> blockRepresentations)
    {
        this.blockRepresentations.addAll(blockRepresentations);
        Validate.isTrue(this.blockRepresentations.size() > 0);
    }

    public static RotatableBlockRepresentation FromConfig(@Nonnull ConfigurationSection configurationSection)
    {
        List<IBlockRepresentation> blockRepresentations = new ArrayList<>();
        ConfigurationSection blocks = configurationSection.getConfigurationSection("blocks");

        for (int i = 0; i < blocks.getKeys(false).size(); ++i)
        {
            blockRepresentations.add(null);
        }

        for (String key : blocks.getKeys(false))
        {
            int index = Integer.parseInt(key);
            blockRepresentations.set(index, IBlockRepresentation.FromConfig(blocks.getConfigurationSection(key)));
        }
        return new RotatableBlockRepresentation(blockRepresentations);
    }

    public List<IBlockRepresentation> getBlockRepresentations()
    {
        return this.blockRepresentations;
    }

    @Override
    public void place(@NotNull Location location, @Nullable BlockFace blockFacePlacedOn)
    {
        int representationToUse = 0;

        // TODO: There has to be a cleaner solution to this ..
        if (blockFacePlacedOn != null)
        {
            if (this.blockRepresentations.size() == 2)
            {
                switch (blockFacePlacedOn)
                {
                    case EAST:
                    case WEST:
                        representationToUse = 1;
                        break;
                }
            }
            else if(this.blockRepresentations.size() == 3)
            {
                switch (blockFacePlacedOn)
                {
                    case EAST:
                    case WEST:
                        representationToUse = 1;
                        break;
                    case UP:
                    case DOWN:
                        representationToUse = 2;
                        break;
                }
            }
            else if(this.blockRepresentations.size() == 4)
            {
                switch (blockFacePlacedOn)
                {
                    case EAST: representationToUse = 1; break;
                    case SOUTH: representationToUse = 2; break;
                    case WEST: representationToUse = 3; break;
                }
            }
            else if(this.blockRepresentations.size() == 6)
            {
                switch (blockFacePlacedOn)
                {
                    case EAST: representationToUse = 1; break;
                    case SOUTH: representationToUse = 2; break;
                    case WEST: representationToUse = 3; break;
                    case UP: representationToUse = 4; break;
                    case DOWN: representationToUse = 5; break;
                }
            }
        }

        this.blockRepresentations.get(representationToUse < this.blockRepresentations.size() ? representationToUse : 0).place(location, blockFacePlacedOn);
    }
}
