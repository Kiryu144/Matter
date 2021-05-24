package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.item.CustomItemBlockInteraction;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class CustomItemTool extends CustomItemDurability
{
    public CustomItemTool(@NotNull ConfigurationSection config, @NotNull String registryName, int registryIndex, short registryUUID)
    {
        super(config, registryName, registryIndex, registryUUID);
    }

    @Override
    public void onBlockChange(@NotNull CustomItemBlockInteraction interaction)
    {
        super.onBlockChange(interaction);

        if (interaction.getInteractionType().equals(CustomItemBlockInteraction.InteractionType.BREAK))
        {
            int damage = this.getItemStackDamage(interaction.getItemStack());
            this.setItemStackDamage(interaction.getItemStack(), damage + 1);
        }
    }
}
