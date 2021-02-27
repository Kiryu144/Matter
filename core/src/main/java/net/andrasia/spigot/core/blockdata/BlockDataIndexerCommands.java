package net.andrasia.spigot.core.blockdata;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.andrasia.spigot.core.Core;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

@CommandAlias("xblock")
public class BlockDataIndexerCommands extends BaseCommand
{
    @Subcommand("index")
    @CommandPermission("xblock.index")
    @CommandCompletion("@blockdata_indexer_types")
    public void setBlock(CommandSender sender, String name, int index)
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

        IBlockDataIndexer blockDataIndexer = Core.getInstance().getBlockDataIndexerRegistry().getIndexer(material);
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
