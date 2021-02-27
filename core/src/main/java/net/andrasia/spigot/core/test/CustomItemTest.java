package net.andrasia.spigot.core.test;

import net.andrasia.spigot.core.Core;
import net.andrasia.spigot.core.config.JsonConfiguration;
import net.andrasia.spigot.core.custom.item.CustomItem;
import net.andrasia.spigot.core.custom.item.CustomItemParser;
import net.andrasia.spigot.core.custom.item.CustomItemRegistry;
import org.apache.commons.lang.Validate;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CustomItemTest extends Test
{
    private CustomItemRegistry customItemRegistry = new CustomItemRegistry();
    private CustomItemParser customItemParser = new CustomItemParser(this.customItemRegistry);


    private final List<ItemStack> itemStackList = new ArrayList<>();
    private final List<CustomItem> customItemList = new ArrayList<>();
    private CustomItem anyCustomItem = null;

    public CustomItemTest()
    {
        super("General Custom Item Test");
    }

    private void add(@Nonnull CustomItem customItem)
    {
        this.itemStackList.add(customItem.createItemStack());
        this.customItemList.add(customItem);
    }

    private void add(@Nonnull ItemStack itemStack)
    {
        this.itemStackList.add(itemStack);
        this.customItemList.add(null);
    }

    @Override
    public void prepareTest() throws IOException, InvalidConfigurationException
    {
        this.add(new ItemStack(Material.STICK));
        this.add(new ItemStack(Material.BONE));
        this.add(new ItemStack(Material.STONE));
        this.add(new ItemStack(Material.COBBLESTONE));
        this.add(new ItemStack(Material.FURNACE));
        this.add(new ItemStack(Material.APPLE));

        JsonConfiguration jsonConfiguration = new JsonConfiguration();
        jsonConfiguration.load(new InputStreamReader(Core.getInstance().getResource("test/CustomItemTest.json")));
        this.customItemParser.parseItems(jsonConfiguration);
        Validate.isTrue(this.customItemRegistry.values().size() >= 4);

        for (CustomItem customItem : this.customItemRegistry.values())
        {
            this.add(customItem);
        }

        this.anyCustomItem = this.customItemList.get(this.customItemList.size() - 1);
    }

    private void singleItemCheck()
    {
        Validate.notNull(this.customItemRegistry.get(this.anyCustomItem.getRegistryIndex()));
        Validate.isTrue(this.customItemRegistry.get(this.anyCustomItem.getRegistryIndex()) == this.anyCustomItem);

        Validate.notNull(this.customItemRegistry.get(this.anyCustomItem.getRegistryName()));
        Validate.isTrue(this.customItemRegistry.get(this.anyCustomItem.getRegistryName()) == this.anyCustomItem);

        ItemStack itemStack = this.anyCustomItem.createItemStack();
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();

        Validate.isTrue(CustomItemRegistry.GetIndex(persistentDataContainer.get(CustomItem.ITEM_REG_INDEX, PersistentDataType.INTEGER)) == this.anyCustomItem.getRegistryIndex());
        Validate.isTrue(CustomItemRegistry.GetID(persistentDataContainer.get(CustomItem.ITEM_REG_INDEX, PersistentDataType.INTEGER)) == this.anyCustomItem.getRegistryID());
    }

    private void multipleItemCheck(boolean convert) throws Exception
    {
        for (int i = 0; i < 500; ++i)
        {
            int randomI = (int) (Math.random() * this.itemStackList.size());
            CustomItem customItem = this.customItemRegistry.get(this.itemStackList.get(randomI), convert);
            if (customItem != this.customItemList.get(randomI))
            {
                throw new Exception("Custom items did not match.");
            }
        }
    }

    @Override
    public void executeTest() throws Exception
    {
        this.singleItemCheck();
        this.multipleItemCheck(false);

        this.customItemRegistry = new CustomItemRegistry();
        for (CustomItem customItem : this.customItemList)
        {
            if (customItem != null)
            {
                this.customItemRegistry.register(customItem);
            }
        }

        this.multipleItemCheck(true);
        this.singleItemCheck();
        this.multipleItemCheck(false);
    }

    @Override
    public void cleanupTest()
    {

    }
}
