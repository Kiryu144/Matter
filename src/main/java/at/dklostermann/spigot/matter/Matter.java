package at.dklostermann.spigot.matter;

import at.dklostermann.spigot.matter.blockdata.MaterialBlockDataIndexerRegistry;
import at.dklostermann.spigot.matter.blockdata.MultipleFacingBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.NoteBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.TripwireBlockDataIndexer;
import at.dklostermann.spigot.matter.config.JsonConfiguration;
import at.dklostermann.spigot.matter.custom.block.CustomBlockCommands;
import at.dklostermann.spigot.matter.custom.block.CustomBlockListener;
import at.dklostermann.spigot.matter.custom.block.CustomBlockRegistry;
import at.dklostermann.spigot.matter.custom.item.CustomItemCommands;
import at.dklostermann.spigot.matter.custom.item.CustomItemListener;
import at.dklostermann.spigot.matter.custom.item.CustomItemRegistry;
import at.dklostermann.spigot.matter.inventory.SmartInventoryManager;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.logging.Logger;

public class Matter extends JavaPlugin
{
    private static Matter instance;

    private final MatterLogger matterLogger = new MatterLogger(this);

    private PaperCommandManager commandManager;
    private MaterialBlockDataIndexerRegistry blockDataIndexerRegistry;
    private SmartInventoryManager smartInventoryManager;

    private CustomItemRegistry customItemRegistry;
    private CustomItemCommands customItemCommands;
    private CustomItemListener customItemListener;

    private CustomBlockRegistry customBlockRegistry;
    private CustomBlockCommands customBlockCommands;
    private CustomBlockListener customBlockListener;

    @Override
    public void onLoad()
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        // Constructors
        this.commandManager = new PaperCommandManager(this);
        this.blockDataIndexerRegistry = new MaterialBlockDataIndexerRegistry();
        this.smartInventoryManager = new SmartInventoryManager(this);

        this.customItemRegistry = new CustomItemRegistry();
        this.customItemCommands = new CustomItemCommands(this.customItemRegistry);
        this.customItemListener = new CustomItemListener(this, this.customItemRegistry);

        this.customBlockRegistry = new CustomBlockRegistry(this.blockDataIndexerRegistry);
        this.customBlockCommands = new CustomBlockCommands(this.customBlockRegistry);
        this.customBlockListener = new CustomBlockListener(this, this.customBlockRegistry, this.customItemRegistry);

        // Data
        this.blockDataIndexerRegistry.register(new MultipleFacingBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new TripwireBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new NoteBlockDataIndexer());

        this.commandManager.getCommandCompletions().registerAsyncCompletion("custom_items", context -> this.customItemRegistry.names());
        this.commandManager.getCommandCompletions().registerAsyncCompletion("custom_blocks", context -> this.customBlockRegistry.names());
        this.commandManager.registerCommand(this.customItemCommands);
        this.commandManager.registerCommand(this.customBlockCommands);
    }

    @Override
    public @NotNull Logger getLogger()
    {
        return this.matterLogger;
    }

    @NotNull
    public FileConfiguration loadConfiguration(@NotNull String filename) throws IOException, InvalidConfigurationException
    {
        FileConfiguration configuration = null;
        if (filename.endsWith(".yml") || filename.endsWith(".yaml"))
        {
            configuration = new YamlConfiguration();
        }
        else if (filename.endsWith(".json"))
        {
            configuration = new JsonConfiguration();
        }
        else
        {
            throw new IllegalArgumentException("Unable to determine configuration type using file ending.");
        }

        configuration.load(filename);
        return configuration;
    }

    public static Matter getInstance()
    {
        return instance;
    }

    public CustomItemRegistry getCustomItemRegistry()
    {
        return this.customItemRegistry;
    }

    public CustomBlockRegistry getCustomBlockRegistry()
    {
        return this.customBlockRegistry;
    }
}