package at.dklostermann.spigot.matter;

import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MatterLogger extends Logger
{
    public MatterLogger(@NotNull Plugin context)
    {
        super(context.getDescription().getName(), null);
        this.setParent(context.getServer().getLogger());
        this.setLevel(Level.ALL);
    }

    @Override
    public void info(String s)
    {
        super.info(ChatColor.GREEN + s);
    }

    @Override
    public void warning(String s)
    {
        super.info(ChatColor.YELLOW + s);
    }

    @Override
    public void severe(String s)
    {
        super.info(ChatColor.RED + s);
    }
}
