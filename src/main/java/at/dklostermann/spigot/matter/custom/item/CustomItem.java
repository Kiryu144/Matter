package at.dklostermann.spigot.matter.custom.item;

import at.dklostermann.spigot.matter.Matter;
import at.dklostermann.spigot.matter.registry.IRegistry;
import at.dklostermann.spigot.matter.registry.IRegistryValue;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
public class CustomItem implements IRegistryValue<CustomItem>
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
     * @see IRegistryValue
     * @see CustomItem#ITEM_NAME_KEY
     * @see CustomItem#ITEM_REG_INDEX
     */
    private final String registryName;
    private short registryIndex;
    private short registryID;

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
     * Lore or Description of an item. Can be accessed by child classes.
     */
    protected List<String> lore = new ArrayList<>();

    public CustomItem(@Nonnull String registryName, @Nonnull ConfigurationSection data) throws CustomItemParseException
    {
        this.registryName = registryName;

        try
        {
            this.customModelData = data.getInt("custom_model_data", -1);
            this.material = Material.valueOf(data.getString("material", "paper").toUpperCase());
            this.lore.addAll(data.getStringList("lore"));
        }
        catch (Exception exception)
        {
            throw new CustomItemParseException(exception);
        }
    }

    public CustomItem(@Nonnull String registryName)
    {
        this.registryName = registryName;
    }

    public void onInteract(@Nonnull CustomItemInteraction interaction)
    {

    }

    public void onBlockChange(@Nonnull CustomItemBlockInteraction interaction)
    {

    }

    @Override
    @Nonnull
    public String getRegistryName()
    {
        return this.registryName;
    }

    @Override
    public int getRegistryIndex()
    {
        return this.registryIndex;
    }

    @Override
    public void setRegistryIndex(@Nonnull IRegistry<? extends IRegistryValue<CustomItem>> owningRegistry, int index)
    {
        Validate.isTrue(index < Short.MAX_VALUE);
        this.registryID = owningRegistry.getInstanceID();
        this.registryIndex = (short) index;
    }

    public short getRegistryID()
    {
        return this.registryID;
    }

    public CustomItem setMaterial(@Nonnull Material material)
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

    /**
     * Builds an itemstack out of the material specified. Will also call setInitialMeta() and setInitialData(),
     * which are used to set any item metadata. Overwriting is possible, but not recommended. For that please refer to
     * setInitalMeta() and setInitialData().
     *
     * @return Newly created ItemStack.
     */
    @Nonnull
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
    protected void setInitialMeta(@Nonnull ItemMeta meta)
    {
        PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();
        this.setInitialData(persistentDataContainer);

        meta.setLocalizedName(String.format("digoncraft.customitem.%s.name", this.getRegistryName()));
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
    protected void setInitialData(@Nonnull PersistentDataContainer persistentDataContainer)
    {
        persistentDataContainer.set(ITEM_NAME_KEY, PersistentDataType.STRING, this.getRegistryName());
        persistentDataContainer.set(ITEM_REG_INDEX, PersistentDataType.INTEGER, CustomItemRegistry.Combine((short) this.getRegistryIndex(), this.registryID));
    }
}
