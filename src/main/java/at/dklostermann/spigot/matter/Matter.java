package at.dklostermann.spigot.matter;

import at.dklostermann.spigot.matter.blockdata.MaterialBlockDataIndexerRegistry;
import at.dklostermann.spigot.matter.blockdata.MultipleFacingBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.NoteBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.TripwireBlockDataIndexer;
import at.dklostermann.spigot.matter.config.JsonConfiguration;
import at.dklostermann.spigot.matter.custom.CustomGameObject;
import at.dklostermann.spigot.matter.custom.CustomGameObjects;
import at.dklostermann.spigot.matter.custom.block.*;
import at.dklostermann.spigot.matter.custom.item.*;
import at.dklostermann.spigot.matter.custom.gui.InventoryGui;
import at.dklostermann.spigot.matter.custom.gui.InventoryGuiListener;
import at.dklostermann.spigot.matter.custom.gui.button.GuiGiveItemButton;
import at.dklostermann.spigot.matter.custom.gui.inventory.BukkitInventory;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class Matter extends JavaPlugin
{
    private static Matter instance;
    private final MatterLogger matterLogger = new MatterLogger(this);
    private final MaterialBlockDataIndexerRegistry blockDataIndexerRegistry = new MaterialBlockDataIndexerRegistry();

    private CustomGameObjects<CustomItem> customItems;
    private CustomGameObjects<CustomBlock> customBlocks;

    private InventoryGuiListener inventoryGuiListener;
    private InventoryGui customItemGUI;

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

        final CustomItemRegistry customItemRegistry = new CustomItemRegistry();
        this.customItems = new CustomGameObjects<>(customItemRegistry, new CustomItemListener(this, customItemRegistry), new CustomItemCommands(customItemRegistry), new CustomItemParser(customItemRegistry));
        this.commandManager.registerCommand(this.customItems.getCommands());

        final CustomBlockRegistry customBlockRegistry = new CustomBlockRegistry();
        this.customBlocks = new CustomGameObjects<>(customBlockRegistry, new CustomBlockListener(this, customBlockRegistry), new CustomBlockCommands(customBlockRegistry), new CustomBlockParser(customBlockRegistry, customItemRegistry));
        this.commandManager.registerCommand(this.customBlocks.getCommands());

        this.blockDataIndexerRegistry.register(new MultipleFacingBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new TripwireBlockDataIndexer());
        this.blockDataIndexerRegistry.register(new NoteBlockDataIndexer());

        this.inventoryGuiListener = new InventoryGuiListener(this);

        this.reload(this.getServer().getConsoleSender());
    }

    @Override
    public @NotNull Logger getLogger()
    {
        return this.matterLogger;
    }

    @Nonnull
    public FileConfiguration loadConfiguration(@Nonnull String filename) throws IOException, InvalidConfigurationException
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

    public <T extends CustomGameObject> void reloadGameObject(@Nonnull CustomGameObjects<T> customGameObjects, @Nullable CommandSender commandSender)
    {
        if (customGameObjects.getParser() != null)
        {
            final Path path = Paths.get(this.getDataFolder().getAbsolutePath(), customGameObjects.getParser().getConfigName() + ".json");
            final File file = path.toFile();
            file.getParentFile().mkdirs();

            JsonConfiguration jsonConfiguration = new JsonConfiguration();
            try
            {
                jsonConfiguration.load(file);
                customGameObjects.getParser().parseAll(jsonConfiguration);
            } catch (IOException | InvalidConfigurationException e)
            {
                e.printStackTrace();
                this.getServer().shutdown();
            }
        }

        if (customGameObjects.getCommands() != null)
        {
            customGameObjects.getCommands().setRegistry(customGameObjects.getRegistry());
        }
    }

    public void reload(@Nullable CommandSender commandSender)
    {
        this.reloadGameObject(this.customItems, commandSender);
        this.reloadGameObject(this.customBlocks, commandSender);

        Bukkit.getOnlinePlayers().forEach(player -> this.getCustomItemRegistry().fixInventory(player.getInventory()));

        this.customItemGUI = new InventoryGui(new BukkitInventory(9*6, "Custom Item GUI"));
        int slot = 0;
        for (CustomItem customItem : this.getCustomItemRegistry().values())
        {
            this.customItemGUI.setButton(slot++, new GuiGiveItemButton(customItem.createItemStack()));
        }
        this.inventoryGuiListener.register(this.customItemGUI);
    }

    @Nonnull
    public MaterialBlockDataIndexerRegistry getBlockDataIndexerRegistry()
    {
        return this.blockDataIndexerRegistry;
    }

    @Nonnull
    public CustomItemRegistry getCustomItemRegistry()
    {
        return (CustomItemRegistry) this.customItems.getRegistry();
    }

    @Nonnull
    public CustomBlockRegistry getCustomBlockRegistry()
    {
        return (CustomBlockRegistry) this.customBlocks.getRegistry();
    }

    @Nonnull
    public InventoryGui getCustomItemGUI()
    {
        return this.customItemGUI;
    }

    @Nonnull
    public InventoryGuiListener getInventoryGuiListener()
    {
        return this.inventoryGuiListener;
    }
}