package net.andrasia.spigot.core.custom.item.types;

import net.andrasia.spigot.core.custom.block.CustomBlock;
import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.custom.item.CustomItemBlockInteraction;
import net.andrasia.spigot.core.custom.item.CustomItemParseException;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CustomItemBlock extends CustomItem
{
    private CustomBlock customBlock;

    public CustomItemBlock(@Nonnull String registryName, @Nonnull ConfigurationSection data) throws CustomItemParseException
    {
        super(registryName, data);
    }

    public CustomItemBlock(@NotNull String registryName)
    {
        super(registryName);
    }

    public void setCustomBlock(@Nonnull CustomBlock customBLock)
    {
        this.customBlock = customBLock;
    }

    @Override
    public void onBlockChange(@Nonnull CustomItemBlockInteraction interaction)
    {
        if (interaction.getInteractionType().equals(CustomItemBlockInteraction.InteractionType.PLACE))
        {
            this.customBlock.place(interaction.getBlock().getLocation(), interaction.getBlockFace());
        }
    }
}
