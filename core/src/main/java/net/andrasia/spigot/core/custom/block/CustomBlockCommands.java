package net.andrasia.spigot.core.custom.block;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("core")
public class CustomBlockCommands extends BaseCommand
{
    private CustomBlockRegistry customBlockRegistry;

    public CustomBlockCommands(CustomBlockRegistry customBlockRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
    }

    public void setCustomBlockRegistry(CustomBlockRegistry customBlockRegistry)
    {
        this.customBlockRegistry = customBlockRegistry;
    }

    @Subcommand("setblock")
    @CommandPermission("dc.setblock")
    @CommandCompletion("@custom_blocks")
    public void setBlock(Player player, String name, int x, int y, int z)
    {
        CustomBlock customBlock = this.customBlockRegistry.get(name);
        if (customBlock == null)
        {
            player.sendMessage(String.format("§cUnknown custom block §b%s", name));
            return;
        }
        customBlock.place(new Location(player.getWorld(), x, y, z), null, null);
    }
}
