package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.custom.item.interaction.CustomItemBlockInteraction;
import at.dklostermann.spigot.matter.custom.item.interaction.CustomItemInteraction;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import at.dklostermann.spigot.matter.registry.RegistryValue;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * The base class for any custom item. As of 1.16.5, it is impossible to add new items to the existing materials through
 * the vanilla game. Our approach to implement them is therefore a mixture of using existing items and a resourcepack.
 *
 * Every item has an optional property named "CustomModelData"(integer). It allows the resourcepack to overwrite
 * specific item models/textures using that number. This is how we get the texture right.
 * The item name is also no problem, as we can set the localized name to be specified by the resourcepack in use.
 *
 * Item functionality is added by assigning the item some additional nbt data:
 * > ITEM_NAME_KEY (string): The unique name used to identify that item with a registry.
 * > ITEM_REG_INDEX (int): A combination of an registry index and a registry id.
 *
 * Registry Index: The registry index is a unique number index assigned automatically through the CustomItemRegistry.
 *                 It is used for faster identification of an item. Checking an index in an array is always faster than
 *                 getting the ITEM_NAME_KEY (string) from a HashMap. This index can and will change over restarts in the
 *                 Registry. So it is important to not rely on it being the same over multiple restarts.
 *
 * Registry ID:    This is a solution to the changing index problem described above. Everytime a new registry
 *                 instance is created, a random 2 byte value gets created. The registry ID. Now if checking an ItemStack
 *                 if it is a possible CustomItem, we first compare the registryID stored with the registry ID of the
 *                 registry itself. If they missmatch, we can be sure that the indices are not valid. In that case
 *                 we fetch the corresponding CustomItem through the ITEM_NAME_KEY and fix the stored registryID and
 *                 registryIndex in the item, so the next lookup can be a lot faster.
 */
public class CustomItem extends RegistryValue
{
    /**
     * This is the namespaced key, which stores the registry name in the itemstack meta.
     */
    public static final NamespacedKey ITEM_NAME_KEY = new NamespacedKey(Matter.getInstance(), "cin");

    /**
     * This is the namespaced key, which stores the registry index and registry id in the itemstack meta.
     * We shrink the index & id down to a short, so we can fit both in an integer.
     * The left 2 bytes are the index, the others are for the id.
     * <p>
     * This is done to increase the speed to detect which CustomItem is used for a given ItemStack.
     */
    public static final NamespacedKey ITEM_REG_INDEX = new NamespacedKey(Matter.getInstance(), "iri");

    /**
     * This is the vanilla material to use for the ItemStack representation.
     * This also defines properties like stacksize, durability resolution, etc.
     */
    private Material material = Material.PAPER;

    /**
     * Used for changing the textures using a resourcepack.
     */
    private Integer customModelData = null;

    /**
     * Lore or Description of an item.
     */
    protected List<String> lore = new ArrayList<>();

    public CustomItem(@NotNull IRegistry<? extends IRegistryValue> registry, @NotNull String registryName, int registryIndex)
    {
        super(registry, registryName, registryIndex);
    }

    public CustomItem setMaterial(@NotNull Material material)
    {
        this.material = material;
        return this;
    }

    public CustomItem setCustomModelData(@Nullable Integer customModelData)
    {
        this.customModelData = customModelData;
        return this;
    }

    public CustomItem setLore(@Nullable List<String> lore)
    {
        this.lore = lore;
        return this;
    }

    public void onInteract(@NotNull CustomItemInteraction interaction)
    {

    }

    public void onBlockChange(@NotNull CustomItemBlockInteraction interaction)
    {

    }

    /**
     * Builds an itemstack out of the material specified. Will also call setInitialMeta() and setInitialData(),
     * which are used to set any item metadata. Overwriting is possible, but not recommended. For that please refer to
     * setInitalMeta() and setInitialData().
     *
     * @return Newly created ItemStack.
     */
    @NotNull
    public ItemStack createItemStack()
    {
        ItemStack itemStack = new ItemStack(this.material);
        ItemMeta meta = itemStack.getItemMeta();
        this.setInitialMeta(meta);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    /**
     * Sets any metadata. Is intended to be overwritten by child classes to extend their functionality.
     * super.setInitialMeta() should be called for in all children.
     *
     * @param meta ItemMeta of the item to be created.
     */
    protected void setInitialMeta(@NotNull ItemMeta meta)
    {
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        this.setInitialData(persistentDataContainer);

        meta.setLocalizedName(String.format("matter.customitem.%s.name", this.getRegistryName()));
        meta.setCustomModelData(this.customModelData);
        meta.setLore(this.lore);
    }

    /**
     * Sets initial data for the itemstack on creation. Is intended to be overwritten by child classes to extend their
     * functionality.
     * super.setInitialData() should be called for in all children.
     *
     * @param persistentDataContainer DataContainer of the item to be created.
     */
    protected void setInitialData(@NotNull PersistentDataContainer persistentDataContainer)
    {
        persistentDataContainer.set(ITEM_NAME_KEY, PersistentDataType.STRING, this.getRegistryName());
        persistentDataContainer.set(ITEM_REG_INDEX, PersistentDataType.INTEGER, Combine((short) this.getRegistryIndex(), this.getRegistryUUID()));
    }

    /**
     * Combines two shorts into an integer.
     *
     * @param index left short
     * @param id    right short
     * @return combined integer
     */
    public static int Combine(short index, short id)
    {
        return (index << 16) | (id & 0xFFFF);
    }

    /**
     * @param i Combined integer
     * @return left short
     * @see CustomItem#Combine
     */
    public static short GetIndex(int i)
    {
        return (short) (i >>> 16);
    }

    /**
     * @param i Combined integer
     * @return right short
     * @see CustomItem#Combine
     */
    public static short GetUUID(int i)
    {
        return (short) (i & 0xFFFF);
    }
}
