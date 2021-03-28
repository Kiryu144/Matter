package net.andrasia.spigot.core;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

@CommandAlias("core")
public class CoreCommands extends BaseCommand
{
    @Subcommand("reload")
    @CommandPermission("dc.reload")
    public void reload(CommandSender sender)
    {
        Core.getInstance().reload(sender);
    }
}
