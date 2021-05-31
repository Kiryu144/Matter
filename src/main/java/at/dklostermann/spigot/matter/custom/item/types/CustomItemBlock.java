package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.block.CustomBlock;
import at.dklostermann.spigot.matter.custom.item.CustomItem;
import at.dklostermann.spigot.matter.custom.item.interaction.CustomItemBlockInteraction;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

public class CustomItemBlock extends CustomItem
{
    private CustomBlock customBlock;

    public CustomItemBlock(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex)
    {
        super(registry, registryName, registryIndex);
    }

    public void setCustomBlock(@NotNull CustomBlock customBlock)
    {
        this.customBlock = customBlock;
    }

    @Override
    public void onBlockChange(@NotNull CustomItemBlockInteraction interaction)
    {
        if (this.customBlock == null)
        {
            return;
        }

        if (interaction.getInteractionType().equals(CustomItemBlockInteraction.InteractionType.PLACE))
        {
            interaction.getBlock().setType(Material.AIR);
            this.customBlock.place(interaction.getBlock().getLocation(), interaction.getBlockFace(), interaction.getPlacer());
        }
    }
}
