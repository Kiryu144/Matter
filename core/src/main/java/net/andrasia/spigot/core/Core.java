package net.andrasia.spigot.core;

import co.aikar.commands.PaperCommandManager;
import net.andrasia.spigot.core.blockdata.MaterialBlockDataIndexerRegistry;
import net.andrasia.spigot.core.blockdata.MultipleFacingBlockDataIndexer;
import net.andrasia.spigot.core.blockdata.NoteBlockDataIndexer;
import net.andrasia.spigot.core.blockdata.TripwireBlockDataIndexer;
import net.andrasia.spigot.core.custom.block.CustomBlockListener;
import net.andrasia.spigot.core.custom.block.CustomBlockRegistry;
import net.andrasia.spigot.core.custom.item.CustomItemCommands;
import net.andrasia.spigot.core.custom.item.CustomItemListener;
import net.andrasia.spigot.core.custom.item.CustomItemRegistry;
import net.andrasia.spigot.core.plugin.CorePlugin;

import javax.annotation.Nonnull;

public class Core extends CorePlugin
{
    private static Core instance;
    private final MaterialBlockDataIndexerRegistry blockDataIndexerRegistry = new MaterialBlockDataIndexerRegistry();
    private final CustomItemRegistry customItemRegistry = new CustomItemRegistry();
    private final CustomBlockRegistry customBlockRegistry = new CustomBlockRegistry();

    private CustomItemListener customItemListener = null;
    private CustomBlockListener customBlockListener = null;

    // Library
    private PaperCommandManager commandManager = null;

    @Override
    public void onLoad()
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        this.commandManager = new PaperCommandManager(this);
        this.commandManager.getCommandCompletions().registerCompletion("custom_items", context -> this.customItemRegistry.names());
        this.commandManager.registerCommand(new CustomItemCommands(this.customItemRegistry));

        this.customItemListener = new CustomItemListener(this, this.getCustomItemRegistry());
        this.customBlockListener = new CustomBlockListener(this, this.getCustomBlockRegistry());

        this.blockDataIndexerRegistry.register(new MultipleFacingBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new TripwireBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new NoteBlockDataIndexer());
    }

    public static Core getInstance()
    {
        return instance;
    }

    @Nonnull
    public MaterialBlockDataIndexerRegistry getBlockDataIndexerRegistry()
    {
        return this.blockDataIndexerRegistry;
    }

    @Nonnull
    public CustomItemRegistry getCustomItemRegistry()
    {
        return this.customItemRegistry;
    }

    @Nonnull
    public CustomBlockRegistry getCustomBlockRegistry()
    {
        return this.customBlockRegistry;
    }
}