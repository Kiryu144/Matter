package at.dklostermann.spigot.matter;

import at.dklostermann.spigot.matter.blockdata.IBlockDataIndexer;
import at.dklostermann.spigot.matter.blockdata.MaterialBlockDataIndexerRegistry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("matter")
public class MatterCommands extends BaseCommand
{
    private final MaterialBlockDataIndexerRegistry blockDataIndexerRegistry;

    public MatterCommands(MaterialBlockDataIndexerRegistry blockDataIndexerRegistry)
    {
        this.blockDataIndexerRegistry = blockDataIndexerRegistry;
    }

    @Subcommand("material_index")
    @CommandPermission("matter.material_index")
    @CommandCompletion("@blockdata_indexer_types")
    public void materialIndex(CommandSender sender, String name, int index)
    {
        name = name.toUpperCase();

        Material material = null;
        try
        {
            material = Material.valueOf(name);
        }
        catch (IllegalArgumentException exception)
        {
            sender.sendMessage(String.format("Unknown material type '%s'", name));
            return;
        }

        IBlockDataIndexer blockDataIndexer = this.blockDataIndexerRegistry.getIndexer(material);
        if (blockDataIndexer == null)
        {
            sender.sendMessage(String.format("No indexer registered for material material type '%s'", name));
            return;
        }

        int maxIndex = Math.min(blockDataIndexer.max(), index+10);
        for (int i = Math.max(0, index - 10); i < maxIndex; ++i)
        {
            sender.sendMessage(String.format("%d -> %s", i, blockDataIndexer.fromIndex(i).getAsString()));
        }
    }

    @Subcommand("gui")
    @CommandPermission("matter.gui")
    public void test(Player sender, int title)
    {

        /*
        InputNumberGui inputNumberGui = new InputNumberGui(iResult -> {
            InputNumberGui.Result result = (InputNumberGui.Result) iResult;
            sender.sendMessage("You wrote: " + (result == null ? "null" : result.getValue()));
        });
        Matter.getInstance().getInventoryGuiListener().register(inputNumberGui);
        sender.openInventory(inputNumberGui.getMatterInventory().getInventory());
         */
    }
}













