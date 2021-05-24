package at.dklostermann.spigot.matter.custom.block;

import at.dklostermann.spigot.matter.custom.CustomGameObjectCommand;
import at.dklostermann.spigot.matter.registry.IRegistry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

@CommandAlias("matter")
public class CustomBlockCommands extends CustomGameObjectCommand<CustomBlock>
{
    public CustomBlockCommands(IRegistry<CustomBlock> registry)
    {
        super(registry);
    }

    @Subcommand("setblock")
    @CommandPermission("matter.setblock")
    @CommandCompletion("@custom_blocks")
    public void setBlock(Player player, String name, int x, int y, int z)
    {
        CustomBlock customBlock = this.getRegistry().get(name);
        if (customBlock == null)
        {
            player.sendMessage(String.format("§cUnknown custom block §b%s", name));
            return;
        }
        customBlock.place(new Location(player.getWorld(), x, y, z), null, null);
    }
}
