package at.dklostermann.spigot.matter.gui;

import at.dklostermann.spigot.matter.gui.inventory.MatterInventory;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ExpressionInventoryGui extends InventoryGui
{
    private ExpressionInventoryGui subInventory;
    private final Consumer<IResult> onFinish;

    public ExpressionInventoryGui(@Nonnull MatterInventory matterInventory, @Nullable Consumer<IResult> onFinish)
    {
        super(matterInventory);
        this.onFinish = onFinish;
    }

    public void setSubInventory(@Nonnull ExpressionInventoryGui subInventory)
    {
        this.subInventory = subInventory;
    }

    protected IResult getResult()
    {
        return null;
    }

    @Override
    public void done()
    {
        super.done();
        this.onFinish.accept(this.getResult());
    }

    public interface IResult
    {

    }
}
