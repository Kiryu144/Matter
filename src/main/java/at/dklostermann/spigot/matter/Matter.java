package at.dklostermann.spigot.matter;

import at.dklostermann.spigot.matter.blockdata.MaterialBlockDataIndexerRegistry;
import at.dklostermann.spigot.matter.blockdata.MultipleFacingBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.NoteBlockDataIndexer;
import at.dklostermann.spigot.matter.custom.block.CustomBlockListener;
import at.dklostermann.spigot.matter.custom.block.CustomBlockParser;
import at.dklostermann.spigot.matter.custom.block.CustomBlockRegistry;
import at.dklostermann.spigot.matter.custom.item.CustomItemCommands;
import at.dklostermann.spigot.matter.custom.item.CustomItemListener;
import at.dklostermann.spigot.matter.custom.item.CustomItemRegistry;
import at.dklostermann.spigot.matter.gui.InventoryGuiListener;
import at.dklostermann.spigot.matter.plugin.CorePlugin;
import co.aikar.commands.PaperCommandManager;
import at.dklostermann.spigot.matter.blockdata.TripwireBlockDataIndexer;
import at.dklostermann.spigot.matter.config.JsonConfiguration;
import at.dklostermann.spigot.matter.custom.block.CustomBlockCommands;
import at.dklostermann.spigot.matter.custom.item.CustomItemParser;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.nio.file.Paths;

public class Matter extends CorePlugin
{
    private static Matter instance;
    private final MaterialBlockDataIndexerRegistry blockDataIndexerRegistry = new MaterialBlockDataIndexerRegistry();

    private CustomItemRegistry customItemRegistry;
    private CustomItemListener customItemListener;
    private CustomItemCommands customItemCommands;

    private CustomBlockRegistry customBlockRegistry;
    private CustomBlockListener customBlockListener;
    private CustomBlockCommands customBlockCommands;

    private InventoryGuiListener inventoryGuiListener;

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
        this.commandManager.registerCommand(new MatterCommands());

        this.customItemCommands = new CustomItemCommands(this.customItemRegistry);
        this.customBlockCommands = new CustomBlockCommands(this.customBlockRegistry);
        this.commandManager.registerCommand(this.customItemCommands);
        this.commandManager.registerCommand(this.customBlockCommands);

        this.customItemListener = new CustomItemListener(this, this.getCustomItemRegistry());
        this.customBlockListener = new CustomBlockListener(this);

        this.blockDataIndexerRegistry.register(new MultipleFacingBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new TripwireBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new NoteBlockDataIndexer());

        this.inventoryGuiListener = new InventoryGuiListener(this);

        this.reload(this.getServer().getConsoleSender());
    }

    public static Matter getInstance()
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

    public InventoryGuiListener getInventoryGuiListener()
    {
        return this.inventoryGuiListener;
    }
}