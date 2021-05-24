package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.CustomItemBlockInteraction;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

public class CustomItemBlock extends CustomItem
{
    private CustomBlock customBlock;

    public CustomItemBlock(@NotNull ConfigurationSection config, @NotNull String registryName, int registryIndex, short registryUUID)
    {
        super(config, registryName, registryIndex, registryUUID);
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
