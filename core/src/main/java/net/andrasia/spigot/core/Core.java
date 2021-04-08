package net.andrasia.spigot.core;

import co.aikar.commands.PaperCommandManager;
import net.andrasia.spigot.core.blockdata.MaterialBlockDataIndexerRegistry;
import net.andrasia.spigot.core.blockdata.MultipleFacingBlockDataIndexer;
import net.andrasia.spigot.core.blockdata.NoteBlockDataIndexer;
import net.andrasia.spigot.core.blockdata.TripwireBlockDataIndexer;
import net.andrasia.spigot.core.config.JsonConfiguration;
import net.andrasia.spigot.core.custom.block.CustomBlockCommands;
import net.andrasia.spigot.core.custom.block.CustomBlockListener;
import net.andrasia.spigot.core.custom.block.CustomBlockParser;
import net.andrasia.spigot.core.custom.block.CustomBlockRegistry;
import net.andrasia.spigot.core.custom.item.CustomItemCommands;
import net.andrasia.spigot.core.custom.item.CustomItemListener;
import net.andrasia.spigot.core.custom.item.CustomItemParser;
import net.andrasia.spigot.core.custom.item.CustomItemRegistry;
import net.andrasia.spigot.core.plugin.CorePlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Paths;

public class Core extends CorePlugin
{
    private static Core instance;
    private final MaterialBlockDataIndexerRegistry blockDataIndexerRegistry = new MaterialBlockDataIndexerRegistry();

    private CustomItemRegistry customItemRegistry;
    private CustomItemListener customItemListener;
    private CustomItemCommands customItemCommands;

    private CustomBlockRegistry customBlockRegistry;
    private CustomBlockListener customBlockListener;
    private CustomBlockCommands customBlockCommands;

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
        this.commandManager.registerCommand(new CoreCommands());

        this.customItemCommands = new CustomItemCommands(this.customItemRegistry);
        this.customBlockCommands = new CustomBlockCommands(this.customBlockRegistry);
        this.commandManager.registerCommand(this.customItemCommands);
        this.commandManager.registerCommand(this.customBlockCommands);

        this.customItemListener = new CustomItemListener(this, this.getCustomItemRegistry());
        this.customBlockListener = new CustomBlockListener(this);

        this.blockDataIndexerRegistry.register(new MultipleFacingBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new TripwireBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new NoteBlockDataIndexer());

        this.reload(this.getServer().getConsoleSender());
    }

    public static Core getInstance()
    {
        return instance;
    }

    public void reload(@Nullable CommandSender commandSender)
    {
        try
        {
            CustomItemRegistry customItemRegistry = new CustomItemRegistry();
            CustomItemParser customItemParser = new CustomItemParser(customItemRegistry);

            JsonConfiguration jsonConfiguration = new JsonConfiguration();
            jsonConfiguration.load(Paths.get(this.getDataFolder().toString(), "items.json").toFile());
            customItemParser.parseItems(jsonConfiguration);

            this.customItemRegistry = customItemRegistry;

            this.commandManager.getCommandCompletions().registerCompletion("custom_items", context -> this.customItemRegistry.names());
            if (commandSender != null)
            {
                commandSender.sendMessage(ChatColor.GREEN + String.format("Loaded %d items", this.customItemRegistry.values().size()));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (commandSender != null)
            {
                commandSender.sendMessage(ChatColor.RED + "Unable to load items: " + exception.getMessage());
                commandSender.sendMessage(ChatColor.RED + "View log for more details.");
            }
            this.customItemRegistry = new CustomItemRegistry();
        }
        finally
        {
            this.customItemCommands.setCustomItemRegistry(this.customItemRegistry);
            this.customItemListener.setCustomItemRegistry(this.customItemRegistry);
        }

        // TODO: Prevent code duplication here
        try
        {
            CustomBlockRegistry customBlockRegistry = new CustomBlockRegistry();
            CustomBlockParser customBlockParser = new CustomBlockParser(customBlockRegistry, this.customItemRegistry);

            JsonConfiguration jsonConfiguration = new JsonConfiguration();
            jsonConfiguration.load(Paths.get(this.getDataFolder().toString(), "blocks.json").toFile());
            customBlockParser.parseBlocks(jsonConfiguration);

            this.customBlockRegistry = customBlockRegistry;

            this.commandManager.getCommandCompletions().registerCompletion("custom_blocks", context -> this.customBlockRegistry.names());
            if (commandSender != null)
            {
                commandSender.sendMessage(ChatColor.GREEN + String.format("Loaded %d blocks", this.customBlockRegistry.values().size()));
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            if (commandSender != null)
            {
                commandSender.sendMessage(ChatColor.RED + "Unable to load blocks: " + exception.getMessage());
                commandSender.sendMessage(ChatColor.RED + "View log for more details.");
            }
            this.customBlockRegistry = new CustomBlockRegistry();
        }
        finally
        {
            this.customBlockCommands.setCustomBlockRegistry(this.customBlockRegistry);
            this.customBlockListener.setCustomBlockRegistry(this.customBlockRegistry);
            this.customBlockListener.setCustomItemRegistry(this.customItemRegistry);
        }

        Bukkit.getOnlinePlayers().forEach(player -> this.customItemRegistry.fixInventory(player.getInventory()));
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