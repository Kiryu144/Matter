package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.item.CustomItemParseException;
import at.dklostermann.spigot.matter.custom.item.CustomItemBlockInteraction;
import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class CustomItemTool extends CustomItemDurability
{
    public CustomItemTool(@NotNull String registryName, @NotNull ConfigurationSection data) throws CustomItemParseException
    {
        super(registryName, data);
    }

    public CustomItemTool(@NotNull String registryName)
    {
        super(registryName);
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
