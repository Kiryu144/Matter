package net.andrasia.spigot.aether.item;

import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.custom.item.CustomItemRegistry;
import net.andrasia.spigot.core.custom.item.types.CustomItemPickaxe;
import net.andrasia.spigot.core.custom.item.types.CustomItemTool;
import org.bukkit.Material;

import javax.annotation.Nonnull;

public class AetherItems
{
    public static CustomItem ZANITE_GEMSTONE;
    public static CustomItem AMBROSIUM_SHARD;
    public static CustomItem GOLDEN_AMBER;
    public static CustomItem AECHOR_PETAL;
    public static CustomItem SWETTY_BALL;

    public static CustomItem SKYROOT_PICKAXE;
    public static CustomItem SKYROOT_AXE;
    public static CustomItem SKYROOT_SHOVEL;
    public static CustomItem SKYROOT_SWORD;

    public static CustomItem HOLYSTONE_PICKAXE;
    public static CustomItem HOLYSTONE_AXE;
    public static CustomItem HOLYSTONE_SHOVEL;
    public static CustomItem HOLYSTONE_SWORD;

    public static CustomItem ZANITE_PICKAXE;
    public static CustomItem ZANITE_AXE;
    public static CustomItem ZANITE_SHOVEL;
    public static CustomItem ZANITE_SWORD;

    public static CustomItem GRAVITITE_PICKAXE;
    public static CustomItem GRAVITITE_AXE;
    public static CustomItem GRAVITITE_SHOVEL;
    public static CustomItem GRAVITITE_SWORD;

    public static CustomItem VALKYRIE_PICKAXE;
    public static CustomItem VALKYRIE_AXE;
    public static CustomItem VALKYRIE_SHOVEL;

    public static CustomItem ZANITE_HELMET;
    public static CustomItem ZANITE_CHESTPLATE;
    public static CustomItem ZANITE_LEGGINGS;
    public static CustomItem ZANITE_BOOTS;

    public static CustomItem GRAVITITE_HELMET;
    public static CustomItem GRAVITITE_CHESTPLATE;
    public static CustomItem GRAVITITE_LEGGINGS;
    public static CustomItem GRAVITITE_BOOTS;

    public static CustomItem NEPTUNE_HELMET;
    public static CustomItem NEPTUNE_CHESTPLATE;
    public static CustomItem NEPTUNE_LEGGINGS;
    public static CustomItem NEPTUNE_BOOTS;

    public static CustomItem PHOENIX_HELMET;
    public static CustomItem PHOENIX_CHESTPLATE;
    public static CustomItem PHOENIX_LEGGINGS;
    public static CustomItem PHOENIX_BOOTS;

    public static CustomItem OBSIDIAN_HELMET;
    public static CustomItem OBSIDIAN_CHESTPLATE;
    public static CustomItem OBSIDIAN_LEGGINGS;
    public static CustomItem OBSIDIAN_BOOTS;

    public static CustomItem VALKYRIE_HELMET;
    public static CustomItem VALKYRIE_CHESTPLATE;
    public static CustomItem VALKYRIE_LEGGINGS;
    public static CustomItem VALKYRIE_BOOTS;

    public static CustomItem BLUE_BERRY;
    public static CustomItem GUMMY_SWET;
    public static CustomItem HEALING_STONE;
    public static CustomItem WHITE_APPLE;
    public static CustomItem GINGER_BREAD_MAN;
    public static CustomItem CANDY_CANE;
    public static CustomItem ENCHANTED_BLUEBERRY;

    public static CustomItem SKYROOT_STICK;
    public static CustomItem VICTORY_MEDAL;

    public static CustomItem DUNGEON_KEY;
    public static CustomItem SKYROOT_BUCKET;
    public static CustomItem CLOUD_PARACHUTE;
    public static CustomItem GOLDEN_PARACHUTE;

    public static CustomItem NATURE_STAFF;
    public static CustomItem CLOUD_STAFF;
    public static CustomItem MOA_EGG;

    public static CustomItem DART_SHOOTER;
    public static CustomItem PHOENIX_BOW;
    public static CustomItem DART;

    public static CustomItem FLAMING_SWORD;
    public static CustomItem LIGHTNING_SWORD;
    public static CustomItem HOLY_SWORD;

    public static CustomItem VAMPIRE_BLADE;
    public static CustomItem PIG_SLAYER;
    public static CustomItem CANDY_CANE_SWORD;
    public static CustomItem NOTCH_HAMMER;
    public static CustomItem VALKYRIE_LANCE;

    public static CustomItem LEATHER_GLOVES;
    public static CustomItem IRON_GLOVES;
    public static CustomItem GOLDEN_GLOVES;
    public static CustomItem CHAIN_GLOVES;
    public static CustomItem DIAMOND_GLOVES;

    public static CustomItem ZANITE_GLOVES;
    public static CustomItem GRAVITITE_GLOVES;
    public static CustomItem NEPTUNE_GLOVES;
    public static CustomItem PHOENIX_GLOVES;
    public static CustomItem OBSIDIAN_GLOVES;
    public static CustomItem VALKYRIE_GLOVES;

    public static CustomItem IRON_RING;
    public static CustomItem GOLDEN_RING;
    public static CustomItem ZANITE_RING;
    public static CustomItem ICE_RING;
    public static CustomItem IRON_PENDANT;
    public static CustomItem GOLDEN_PENDANT;
    public static CustomItem ZANITE_PENDANT;
    public static CustomItem ICE_PENDANT;

    public static CustomItem RED_CAPE;
    public static CustomItem BLUE_CAPE;
    public static CustomItem YELLOW_CAPE;
    public static CustomItem WHITE_CAPE;
    public static CustomItem SWET_CAPE;
    public static CustomItem INVISIBILITY_CAPE;
    public static CustomItem AGILITY_CAPE;
    public static CustomItem VALKYRIE_CAPE;

    public static CustomItem GOLDEN_FEATHER;
    public static CustomItem REGENERATION_STONE;
    public static CustomItem IRON_BUBBLE;
    public static CustomItem LIFE_SHARD;

    public static CustomItem SENTRY_BOOTS;
    public static CustomItem LIGHTNING_KNIFE;

    public static CustomItem AETHER_TUNE;
    public static CustomItem ASCENDING_DAWN;
    public static CustomItem WELCOMING_SKIES;
    public static CustomItem LEGACY;

    public static CustomItem REPULSION_SHIELD;

    public static CustomItem LORE_BOOK;
    public static CustomItem DEVELOPER_STICK;
    public static CustomItem SKYROOT_BED_ITEM;
    public static CustomItem AETHER_PORTAL_FRAME;

    public static void Register(@Nonnull CustomItemRegistry customItemRegistry)
    {
        //noinspection UnnecessaryLocalVariable
        CustomItemRegistry r = customItemRegistry;

        SKYROOT_PICKAXE     = register(r, new CustomItemPickaxe("skyroot_pickaxe").setDurability(60).setMaterial(Material.WOODEN_PICKAXE).setCustomModelData(1));
        SKYROOT_AXE         = register(r, new CustomItemPickaxe("skyroot_axe").setDurability(60).setMaterial(Material.WOODEN_AXE).setCustomModelData(1));
        SKYROOT_SHOVEL      = register(r, new CustomItemPickaxe("skyroot_shovel").setDurability(60).setMaterial(Material.WOODEN_SHOVEL).setCustomModelData(1));
        SKYROOT_SWORD       = register(r, new CustomItemPickaxe("skyroot_sword").setDurability(60).setMaterial(Material.WOODEN_SWORD).setCustomModelData(1));

        HOLYSTONE_PICKAXE   = register(r, new CustomItemPickaxe("holystone_pickaxe").setDurability(132).setMaterial(Material.STONE_PICKAXE).setCustomModelData(1));
        HOLYSTONE_AXE       = register(r, new CustomItemPickaxe("holystone_axe").setDurability(132).setMaterial(Material.STONE_AXE).setCustomModelData(1));
        HOLYSTONE_SHOVEL    = register(r, new CustomItemPickaxe("holystone_shovel").setDurability(132).setMaterial(Material.STONE_SHOVEL).setCustomModelData(1));
        HOLYSTONE_SWORD     = register(r, new CustomItemPickaxe("holystone_sword").setDurability(132).setMaterial(Material.STONE_SWORD).setCustomModelData(1));

        ZANITE_PICKAXE      = register(r, new CustomItemPickaxe("zanite_pickaxe").setDurability(251).setMaterial(Material.IRON_PICKAXE).setCustomModelData(1));
        ZANITE_AXE          = register(r, new CustomItemPickaxe("zanite_axe").setDurability(251).setMaterial(Material.IRON_AXE).setCustomModelData(1));
        ZANITE_SHOVEL       = register(r, new CustomItemPickaxe("zanite_shovel").setDurability(251).setMaterial(Material.IRON_SHOVEL).setCustomModelData(1));
        ZANITE_SWORD        = register(r, new CustomItemPickaxe("zanite_sword").setDurability(251).setMaterial(Material.IRON_SWORD).setCustomModelData(1));

        GRAVITITE_PICKAXE   = register(r, new CustomItemPickaxe("gravitite_pickaxe").setDurability(1562).setMaterial(Material.DIAMOND_PICKAXE).setCustomModelData(1));
        GRAVITITE_AXE       = register(r, new CustomItemPickaxe("gravitite_axe").setDurability(1562).setMaterial(Material.DIAMOND_AXE).setCustomModelData(1));
        GRAVITITE_SHOVEL    = register(r, new CustomItemPickaxe("gravitite_shovel").setDurability(1562).setMaterial(Material.DIAMOND_SHOVEL).setCustomModelData(1));
        GRAVITITE_SWORD     = register(r, new CustomItemPickaxe("gravitite_sword").setDurability(1562).setMaterial(Material.DIAMOND_SWORD).setCustomModelData(1));

        VALKYRIE_PICKAXE    = register(r, new CustomItemPickaxe("valkyrie_pickaxe").setDurability(1001).setMaterial(Material.DIAMOND_PICKAXE).setCustomModelData(2));
        VALKYRIE_AXE        = register(r, new CustomItemPickaxe("valkyrie_axe").setDurability(1001).setMaterial(Material.DIAMOND_AXE).setCustomModelData(2));
        VALKYRIE_SHOVEL     = register(r, new CustomItemPickaxe("valkyrie_shovel").setDurability(1001).setMaterial(Material.DIAMOND_SHOVEL).setCustomModelData(2));
    }

    private static CustomItem register(@Nonnull CustomItemRegistry customItemRegistry, @Nonnull CustomItem customItem)
    {
        customItemRegistry.register(customItem);
        return customItem;
    }
}

















