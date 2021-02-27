package net.andrasia.spigot.aether;

import net.andrasia.spigot.aether.item.AetherBlocks;
import net.andrasia.spigot.aether.item.AetherItems;
import net.andrasia.spigot.aether.worldgen.AetherChunkGenerator;
import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.plugin.CorePlugin;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Aether extends CorePlugin
{
    private static Aether instance;

    @Override
    public void onLoad()
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        AetherItems.Register(Core.getInstance().getCustomItemRegistry());
        AetherBlocks.Register(Core.getInstance().getCustomBlockRegistry(), Core.getInstance().getCustomItemRegistry());
    }

    public static Aether getInstance()
    {
        return instance;
    }

    @Override
    public @Nullable ChunkGenerator getDefaultWorldGenerator(@NotNull String worldName, @Nullable String id)
    {
        return new AetherChunkGenerator();
    }
}
