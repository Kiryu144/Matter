package at.dklostermann.spigot.matter.custom.item;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomItemBlockInteraction
{
    public enum InteractionType { BREAK, PLACE }

    private final InteractionType interactionType;
    private final EquipmentSlot hand;
    private final ItemStack itemStack;
    private final CustomItem customItem;
    private final Player placer;
    private final Block block;
    private final BlockFace blockFace;

    public CustomItemBlockInteraction(InteractionType interactionType, EquipmentSlot hand, ItemStack itemStack, CustomItem customItem, Player placer, Block block, BlockFace blockFace)
    {
        this.interactionType = interactionType;
        this.hand = hand;
        this.itemStack = itemStack;
        this.customItem = customItem;
        this.placer = placer;
        this.block = block;
        this.blockFace = blockFace;
    }

    @Nonnull
    public InteractionType getInteractionType()
    {
        return this.interactionType;
    }

    @Nonnull
    public EquipmentSlot getHand()
    {
        return this.hand;
    }

    @Nonnull
    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    @Nonnull
    public Block getBlock()
    {
        return this.block;
    }

    @Nullable
    public BlockFace getBlockFace()
    {
        return this.blockFace;
    }

    @Nonnull
    public CustomItem getCustomItem()
    {
        return this.customItem;
    }

    @Nullable
    public Player getPlacer()
    {
        return this.placer;
    }
}
