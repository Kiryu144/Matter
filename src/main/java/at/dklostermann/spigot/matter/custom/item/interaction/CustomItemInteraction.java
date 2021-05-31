package at.dklostermann.spigot.matter.custom.item.interaction;

import at.dklostermann.spigot.matter.custom.item.CustomItem;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CustomItemInteraction
{
    private final EquipmentSlot hand;
    private final boolean attack;
    private final ItemStack itemStack;
    private final CustomItem customItem;
    private final LivingEntity holder;
    private final Block block;
    private final BlockFace blockFace;
    private final Entity entity;

    public CustomItemInteraction(EquipmentSlot hand, boolean attack, ItemStack itemStack, CustomItem customItem, LivingEntity holder, Entity entity)
    {
        this.hand = hand;
        this.attack = attack;
        this.itemStack = itemStack;
        this.customItem = customItem;
        this.holder = holder;
        this.entity = entity;
        this.block = null;
        this.blockFace = null;
    }

    public CustomItemInteraction(EquipmentSlot hand, boolean attack, ItemStack itemStack, CustomItem customItem, LivingEntity holder, Block block, BlockFace blockFace)
    {
        this.hand = hand;
        this.attack = attack;
        this.itemStack = itemStack;
        this.customItem = customItem;
        this.holder = holder;
        this.block = block;
        this.blockFace = blockFace;
        this.entity = null;
    }

    @Nonnull
    public CustomItem getCustomItem()
    {
        return this.customItem;
    }

    @Nonnull
    public LivingEntity getHolder()
    {
        return this.holder;
    }

    @Nonnull
    public EquipmentSlot getHand()
    {
        return this.hand;
    }

    public boolean isAttack()
    {
        return this.attack;
    }

    @Nonnull
    public ItemStack getItemStack()
    {
        return this.itemStack;
    }

    @Nullable
    public Block getBlock()
    {
        return this.block;
    }

    @Nullable
    public BlockFace getBlockFace()
    {
        return this.blockFace;
    }

    @Nullable
    public Entity getEntity()
    {
        return this.entity;
    }


}
