package at.dklostermann.spigot.matter.custom.item;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.TranslatableComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

@CommandAlias("matter")
public class CustomItemCommands extends BaseCommand
{
    private CustomItemRegistry customItemRegistry;

    public CustomItemCommands(CustomItemRegistry customItemRegistry)
    {
        this.customItemRegistry = customItemRegistry;
    }

    public void setCustomItemRegistry(CustomItemRegistry customItemRegistry)
    {
        this.customItemRegistry = customItemRegistry;
    }

    @Subcommand("give")
    @CommandPermission("dc.give")
    @CommandCompletion("@players @custom_items")
    public void give(CommandSender sender, OnlinePlayer receiver, String customItemName, @Default("1") int amount)
    {
        Player receiverPlayer = receiver != null ? receiver.getPlayer() : null;
        if (receiver == null && sender instanceof Player)
        {
            receiverPlayer = (Player) sender;
        }

        if (receiverPlayer == null)
        {
            sender.sendMessage(ChatColor.RED + "Player not found");
            return;
        }

        CustomItem customItem = this.customItemRegistry.get(customItemName);
        if (customItem == null)
        {
            sender.sendMessage(ChatColor.RED + "Unknown custom item " + customItemName);
            return;
        }

        ItemStack itemStack = customItem.createItemStack();
        itemStack.setAmount(amount);
        receiverPlayer.getInventory().addItem(itemStack);

        TextComponent item = new TextComponent("[");
        item.addExtra(new TranslatableComponent(itemStack.getItemMeta().getLocalizedName()));
        item.addExtra("]");

        TextComponent gave = new TextComponent(String.format("Gave %d ", itemStack.getAmount()));
        gave.addExtra(item);
        gave.addExtra(String.format(" to %s", receiverPlayer.getName()));

        sender.spigot().sendMessage(gave);

        if (sender != receiverPlayer)
        {
            TextComponent received = new TextComponent(String.format("Received %d ", itemStack.getAmount()));
            received.addExtra(item);
            received.addExtra(String.format(" from %s", sender.getName()));
            receiverPlayer.spigot().sendMessage(received);
        }
    }

    @Subcommand("giveall")
    @CommandPermission("dc.giveall")
    @CommandCompletion("@players")
    public void give(CommandSender sender, OnlinePlayer receiver)
    {
        Player receiverPlayer = receiver != null ? receiver.getPlayer() : null;
        if (receiver == null && sender instanceof Player)
        {
            receiverPlayer = (Player) sender;
        }

        if (receiverPlayer == null)
        {
            sender.sendMessage(ChatColor.RED + "Player not found");
            return;
        }

        Location location = receiverPlayer.getLocation();
        for (CustomItem customItem : this.customItemRegistry.values())
        {
            location.getWorld().dropItem(location, customItem.createItemStack());
        }

        TextComponent gave = new TextComponent("Gave all custom items");
        gave.addExtra(String.format(" to %s", receiverPlayer.getName()));

        sender.spigot().sendMessage(gave);

        if (sender != receiverPlayer)
        {
            TextComponent received = new TextComponent("Received all custom items");
            received.addExtra(String.format(" from %s", sender.getName()));
            receiverPlayer.spigot().sendMessage(received);
        }
    }
}














