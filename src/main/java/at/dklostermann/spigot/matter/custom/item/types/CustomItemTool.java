package at.dklostermann.spigot.matter.custom.item.types;

import at.dklostermann.spigot.matter.custom.item.interaction.CustomItemBlockInteraction;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.jetbrains.annotations.NotNull;

public class CustomItemTool extends CustomItemDurability
{
    public CustomItemTool(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex)
    {
        super(registry, registryName, registryIndex);
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
