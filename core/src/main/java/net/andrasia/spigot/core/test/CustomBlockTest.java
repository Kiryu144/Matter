package net.andrasia.spigot.core.test;

import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.blockdata.IBlockDataIndexer;
import net.andrasia.spigot.core.custom.block.CustomBlock;
import net.andrasia.spigot.core.custom.block.CustomBlockRegistry;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;

import javax.annotation.Nonnull;
import java.util.List;

public class CustomBlockTest extends Test
{
    private final CustomBlockRegistry registry = new CustomBlockRegistry();
    private Location testLocation = null;
    private Material originalMaterial = null;
    private BlockData originalBlockData = null;

    public CustomBlockTest()
    {
        super("Custom Block Test");
    }

    @Nonnull
    private Material getRandomBlockMaterial()
    {
        List<Material> mats = Core.getInstance().getBlockDataIndexerRegistry().getKnownMaterials();
        return mats.get((int) (mats.size() * Math.random()));
    }

    @Override
    public void prepareTest()
    {
        return; // TODO
        /*
        if (Core.getInstance().getBlockDataIndexerRegistry().getKnownMaterials().size() == 0)
        {
            return;
        }

        for (Material material : Core.getInstance().getBlockDataIndexerRegistry().getKnownMaterials())
        {
            IBlockDataIndexer indexer = Core.getInstance().getBlockDataIndexerRegistry().getIndexer(material);
            for (int i = 0; i < indexer.max(); ++i)
            {
                this.registry.register(new CustomBlock(String.valueOf(Math.random()), material, i));
            }
        }

        this.testLocation = Bukkit.getWorlds().get(0).getSpawnLocation();
        this.testLocation.setY(250);
        this.originalMaterial = this.testLocation.getBlock().getType();
        this.originalBlockData = this.testLocation.getBlock().getBlockData().clone();*/
    }

    @Override
    public void executeTest() throws Exception
    {
        if (Core.getInstance().getBlockDataIndexerRegistry().getKnownMaterials().size() == 0)
        {
            return;
        }

        for (CustomBlock customBlock : this.registry.values())
        {
            customBlock.place(this.testLocation);
            CustomBlock found = this.registry.getCustomBlock(this.testLocation.getBlock());

            if (customBlock != found)
            {
                throw new Exception(String.format("Missmatch: %s:%d != %s:%d", customBlock.getMaterial(), customBlock.getMaterialIndicies(), found.getMaterial(), found.getMaterialIndicies()));
            }
        }
    }

    @Override
    public void cleanupTest()
    {
        Block block = this.testLocation.getBlock();
        block.setType(this.originalMaterial, false);
        block.setBlockData(this.originalBlockData, false);
    }
}
