package net.andrasia.spigot.core.custom.block.representation;

import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.custom.block.CustomBlock;
import net.andrasia.spigot.core.custom.item.CustomItem;
import org.apache.commons.lang.Validate;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ItemFrameBlockRepresentation implements IBlockRepresentation
{
    private final ItemStack itemStack;

    public ItemFrameBlockRepresentation(@Nonnull ItemStack itemStack)
    {
        this.itemStack = itemStack;
    }

    @Nonnull
    public static ItemFrameBlockRepresentation FromConfig(@Nonnull ConfigurationSection configurationSection)
    {
        ConfigurationSection displaySection = configurationSection.getConfigurationSection("display");
        Validate.notNull(displaySection);

        Material material = Material.valueOf(displaySection.getString("material").toUpperCase());
        int customModelData = displaySection.getInt("custom_model_data");

        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setCustomModelData(customModelData);
        itemStack.setItemMeta(meta);

        return new ItemFrameBlockRepresentation(itemStack);
    }

    @Override
    public void place(@Nonnull CustomBlock customBlock, @Nonnull Location location, @javax.annotation.Nullable BlockFace blockFace, @Nullable Player player)
    {
        location.getWorld().spawnEntity(location, EntityType.ITEM_FRAME, CreatureSpawnEvent.SpawnReason.DEFAULT, entity -> {
            ItemFrame itemFrame = (ItemFrame) entity;
            itemFrame.setFacingDirection(blockFace != null ? blockFace : BlockFace.DOWN);
            itemFrame.setItem(this.itemStack.clone(), false);
            itemFrame.setCustomName(customBlock.getRegistryName());
            itemFrame.setFixed(true);
            itemFrame.setInvulnerable(true);
            itemFrame.setVisible(false);
            itemFrame.setCustomNameVisible(false);
        });
        location.getBlock().setType(Material.BARRIER);
    }
}
