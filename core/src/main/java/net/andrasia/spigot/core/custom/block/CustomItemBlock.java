package net.andrasia.spigot.core.custom.block;

import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.custom.item.CustomItemBlockInteraction;
import net.andrasia.spigot.core.custom.item.CustomItemParseException;
import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.Validate;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;

public class CustomItemBlock extends CustomItem
{
    private final CustomBlock customBlock;

    public CustomItemBlock(@Nonnull String registryName, @Nonnull ConfigurationSection data) throws CustomItemParseException
    {
        super(registryName, data);
        
        try
        {
            String customBlockName = data.getString("custom_block");
            Validate.notNull(customBlockName, "Missing key 'custom_block'");
            throw new NotImplementedException();
            //this.customBlock = Core.getInstance().getCustomBlockRegistry().get(customBlockName);
            //Validate.notNull(this.customBlock, String.format("Unknown custom block '%s'", customBlockName));
        }
        catch (Exception exception)
        {
            throw new CustomItemParseException(exception);
        }
    }

    @Override
    public void onBlockChange(@Nonnull CustomItemBlockInteraction interaction)
    {
        if (interaction.getInteractionType().equals(CustomItemBlockInteraction.InteractionType.PLACE))
        {
            this.customBlock.place(interaction.getBlock().getLocation());
        }
    }
}
