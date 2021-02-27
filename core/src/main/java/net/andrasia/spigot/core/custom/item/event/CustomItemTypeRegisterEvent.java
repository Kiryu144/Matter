package net.andrasia.spigot.core.custom.item.event;

import net.andrasia.spigot.core.custom.item.CustomItemParser;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import javax.annotation.Nonnull;

public class CustomItemTypeRegisterEvent extends Event
{
    private final CustomItemParser customItemParser;

    public CustomItemTypeRegisterEvent(@Nonnull CustomItemParser customItemParser)
    {
        this.customItemParser = customItemParser;
    }

    @Nonnull
    public CustomItemParser getCustomItemParser()
    {
        return this.customItemParser;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
