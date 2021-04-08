package net.andrasia.spigot.aether.worldgen;

import net.andrasia.spigot.aether.item.AetherBlocks;
import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.custom.block.CustomBlock;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.BlockPopulator;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class AetherTreePopulator extends BlockPopulator
{
    @Override
    public void populate(@NotNull World world, @NotNull Random random, @NotNull Chunk chunk)
    {
        for (int x = 0; x < 16; ++x)
        {
            for (int z = 0; z < 16; ++z)
            {
                if (random.nextInt(1000) > 3)
                {
                    continue;
                }

                Location loc = world.getHighestBlockAt(chunk.getX() * 16 + x, chunk.getZ() * 16 + z).getLocation();
                this.generate(world, random, loc);
            }
        }
    }

    public boolean generate(World world, Random random, Location pos)
    {
        int l = random.nextInt(3) + 4;

        CustomBlock j1c = Core.getInstance().getCustomBlockRegistry().getCustomBlock(pos.getBlock());

        if (j1c != AetherBlocks.AETHER_GRASS)
        {
            return false;
        }

        AetherBlocks.AETHER_DIRT.place(pos);

        pos = pos.add(0, 1, 0);

        for(int k1 = (pos.getBlockY() - 3) + l; k1 <= pos.getY() + l; k1++)
        {
            int j2 = k1 - (pos.getBlockY() + l);
            int i3 = 1 - j2 / 2;

            for(int k3 = pos.getBlockX() - i3; k3 <= pos.getX() + i3; k3++)
            {
                int l3 = k3 - pos.getBlockX();

                for(int i4 = pos.getBlockZ() - i3; i4 <= pos.getZ() + i3; i4++)
                {
                    int j4 = i4 - pos.getBlockZ();

                    Block newPos = world.getBlockAt(k3, k1, i4);

                    if((Math.abs(l3) != i3 || Math.abs(j4) != i3 || random.nextInt(2) != 0 && j2 != 0) && !newPos.getType().isSolid())
                    {
                        newPos.setType(Material.BIRCH_LEAVES);
                    }
                }

            }
        }

        Location loc = pos;
        for(int l1 = 0; l1 < l; l1++)
        {
            loc.getBlock().setType(Material.OAK_LOG);
            loc.add(0, 1, 0);
        }

        return true;
    }
}
