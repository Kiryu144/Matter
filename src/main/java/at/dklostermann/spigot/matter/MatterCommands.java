package at.dklostermann.spigot.matter;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import at.dklostermann.spigot.matter.blockdata.IBlockDataIndexer;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

@CommandAlias("matter")
public class MatterCommands extends BaseCommand
{
    @Subcommand("reload")
    @CommandPermission("dc.reload")
    public void reload(CommandSender sender)
    {
        Matter.getInstance().reload(sender);
    }

    @Subcommand("material_index")
    @CommandPermission("xblock.index")
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

        IBlockDataIndexer blockDataIndexer = Matter.getInstance().getBlockDataIndexerRegistry().getIndexer(material);
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
}