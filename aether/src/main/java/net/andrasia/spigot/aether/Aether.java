package net.andrasia.spigot.aether;

import net.andrasia.spigot.aether.item.AetherItems;
import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.plugin.CorePlugin;

public class Aether extends CorePlugin
{
    private static Aether instance;

    @Override
    public void onLoad()
    {
        instance = this;
    }

    @Override
    public void onEnable()
    {
        AetherItems.Register(Core.getInstance().getCustomItemRegistry());
    }

    public static Aether getInstance()
    {
        return instance;
    }
}
