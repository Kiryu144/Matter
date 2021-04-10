package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.CustomItemBlockInteraction;
import at.dklostermann.spigot.matter.custom.item.CustomItemParseException;
import org.bukkit.Material;
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
            interaction.getBlock().setType(Material.AIR);
            this.customBlock.place(interaction.getBlock().getLocation(), interaction.getBlockFace(), interaction.getPlacer());
        }
    }
}