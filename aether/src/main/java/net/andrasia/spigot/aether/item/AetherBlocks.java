package net.andrasia.spigot.aether.item;

import net.andrasia.spigot.core.custom.block.CustomBlock;
import net.andrasia.spigot.core.custom.block.CustomBlockRegistry;
import net.andrasia.spigot.core.custom.item.CustomItemRegistry;
import net.andrasia.spigot.core.custom.item.types.CustomItemBlock;
import org.bukkit.Material;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AetherBlocks
{
    public static CustomBlock AETHER_GRASS;
    public static CustomBlock ENCHANTED_AETHER_GRASS;
    public static CustomBlock AETHER_DIRT;
    public static CustomBlock HOLYSTONE;
    public static CustomBlock MOSSY_HOLYSTONE;
    public static CustomBlock HOLYSTONE_BRICK;
    public static CustomBlock AERCLOUD;
    public static CustomBlock QUICKSOIL;
    public static CustomBlock ICESTONE;
    public static CustomBlock AMBROSIUM_ORE;
    public static CustomBlock ZANITE_ORE;
    public static CustomBlock GRAVITITE_ORE;
    public static CustomBlock AETHER_LEAVES;
    public static CustomBlock AETHER_LOG;
    public static CustomBlock SKYROOT_PLANK;
    public static CustomBlock QUICKSOIL_GLASS;
    public static CustomBlock AEROGEL;
    public static CustomBlock ENCHANTED_GRAVITITE;
    public static CustomBlock ZANITE_BLOCK;
    public static CustomBlock BERRY_BUSH;
    public static CustomBlock BERRY_BUSH_STEM;
    public static CustomBlock ENCHANTER;
    public static CustomBlock FREEZER;
    public static CustomBlock INCUBATOR;
    public static CustomBlock AMBROSIUM_TORCH;
    public static CustomBlock AETHER_PORTAL;
    public static CustomBlock CHEST_MIMIC;
    public static CustomBlock TREASURE_CHEST;
    public static CustomBlock DUNGEON_TRAP;
    public static CustomBlock DUNGEON_BLOCK;
    public static CustomBlock LOCKED_DUNGEON_BLOCK;
    public static CustomBlock PURPLE_FLOWER;
    public static CustomBlock WHITE_FLOWER;
    public static CustomBlock SKYROOT_SAPLING;
    public static CustomBlock GOLDEN_OAK_SAPLING;
    public static CustomBlock CRYSTAL_LEAVES;
    public static CustomBlock PILLAR;
    public static CustomBlock PILLAR_TOP;
    public static CustomBlock SKYROOT_FENCE;
    public static CustomBlock SKYROOT_FENCE_GATE;
    public static CustomBlock CARVED_STAIRS;
    public static CustomBlock ANGELIC_STAIRS;
    public static CustomBlock HELLFIRE_STAIRS;
    public static CustomBlock SKYROOT_STAIRS;
    public static CustomBlock MOSSY_HOLYSTONE_STAIRS;
    public static CustomBlock HOLYSTONE_STAIRS;
    public static CustomBlock HOLYSTONE_BRICK_STAIRS;
    public static CustomBlock AEROGEL_STAIRS;
    public static CustomBlock CARVED_SLAB;
    public static CustomBlock ANGELIC_SLAB;
    public static CustomBlock HELLFIRE_SLAB;
    public static CustomBlock SKYROOT_SLAB;
    public static CustomBlock HOLYSTONE_SLAB;
    public static CustomBlock HOLYSTONE_BRICK_SLAB;
    public static CustomBlock MOSSY_HOLYSTONE_SLAB;
    public static CustomBlock AEROGEL_SLAB;
    public static CustomBlock CARVED_DOUBLE_SLAB;
    public static CustomBlock ANGELIC_DOUBLE_SLAB;
    public static CustomBlock HELLFIRE_DOUBLE_SLAB;
    public static CustomBlock SKYROOT_DOUBLE_SLAB;
    public static CustomBlock HOLYSTONE_DOUBLE_SLAB;
    public static CustomBlock HOLYSTONE_BRICK_DOUBLE_SLAB;
    public static CustomBlock MOSSY_HOLYSTONE_DOUBLE_SLAB;
    public static CustomBlock AEROGEL_DOUBLE_SLAB;
    public static CustomBlock HOLYSTONE_WALL;
    public static CustomBlock MOSSY_HOLYSTONE_WALL;
    public static CustomBlock HOLYSTONE_BRICK_WALL;
    public static CustomBlock CARVED_WALL;
    public static CustomBlock ANGELIC_WALL;
    public static CustomBlock HELLFIRE_WALL;
    public static CustomBlock AEROGEL_WALL;
    public static CustomBlock HOLIDAY_LEAVES;
    public static CustomBlock PRESENT;
    public static CustomBlock SUN_ALTAR;
    public static CustomBlock SKYROOT_BOOKSHELF;
    public static CustomBlock SKYROOT_BED;

    public static void Register(@Nonnull CustomBlockRegistry customBlockRegistry, @Nullable CustomItemRegistry customItemRegistry)
    {
        //noinspection UnnecessaryLocalVariable
        CustomBlockRegistry r1 = customBlockRegistry;
        //noinspection UnnecessaryLocalVariable
        CustomItemRegistry r2 = customItemRegistry;

        AETHER_GRASS = Register(r1, r2, new CustomBlock("aether_grass").setMaterial(Material.BROWN_MUSHROOM_BLOCK, 0),
                (CustomItemBlock) new CustomItemBlock("aether_grass").setMaterial(Material.GRASS_BLOCK).setCustomModelData(1));

        AETHER_DIRT = Register(r1, r2, new CustomBlock("aether_dirt").setMaterial(Material.BROWN_MUSHROOM_BLOCK, 1),
                (CustomItemBlock) new CustomItemBlock("aether_dirt").setMaterial(Material.DIRT).setCustomModelData(1));

        HOLYSTONE = Register(r1, r2, new CustomBlock("holystone").setMaterial(Material.BROWN_MUSHROOM_BLOCK, 2),
                (CustomItemBlock) new CustomItemBlock("holystone").setMaterial(Material.STONE).setCustomModelData(1));

        QUICKSOIL = Register(r1, r2, new CustomBlock("quicksoil").setMaterial(Material.BROWN_MUSHROOM_BLOCK, 3),
                (CustomItemBlock) new CustomItemBlock("quicksoil").setMaterial(Material.SAND).setCustomModelData(1));
    }

    private static CustomBlock Register(@Nonnull CustomBlockRegistry customBlockRegistry, @Nullable CustomItemRegistry customItemRegistry, @Nonnull CustomBlock customBlock, @Nullable CustomItemBlock customItemBlock)
    {
        customBlock.setItem(customItemBlock);
        customBlockRegistry.register(customBlock);

        if (customItemBlock != null && customItemRegistry != null)
        {
            customItemBlock.setCustomBlock(customBlock);
            customItemRegistry.register(customItemBlock);
        }

        return customBlock;
    }
}














