package at.dklostermann.spigot.matter;

import at.dklostermann.spigot.matter.gui.ExpressionInventoryGui;
import at.dklostermann.spigot.matter.gui.button.GuiLambdaButton;
import at.dklostermann.spigot.matter.gui.inventory.CustomInventory;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import at.dklostermann.spigot.matter.blockdata.IBlockDataIndexer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.function.Consumer;

@CommandAlias("matter")
public class MatterCommands extends BaseCommand
{
    @Subcommand("reload")
    @CommandPermission("matter.reload")
    public void reload(CommandSender sender)
    {
        Matter.getInstance().reload(sender);
    }

    @Subcommand("material_index")
    @CommandPermission("matter.material_index")
    @CommandCompletion("@blockdata_indexer_types")
    public void materialIndex(CommandSender sender, String name, int index)
    {
        name = name.toUpperCase();

        Material material = null;
        try
        {
            material = Material.valueOf(name);
        }
        catch (IllegalArgumentException exception)
        {
            sender.sendMessage(String.format("Unknown material type '%s'", name));
            return;
        }

        IBlockDataIndexer blockDataIndexer = Matter.getInstance().getBlockDataIndexerRegistry().getIndexer(material);
        if (blockDataIndexer == null)
        {
            sender.sendMessage(String.format("No indexer registered for material material type '%s'", name));
            return;
        }

        int maxIndex = Math.min(blockDataIndexer.max(), index+10);
        for (int i = Math.max(0, index - 10); i < maxIndex; ++i)
        {
            sender.sendMessage(String.format("%d -> %s", i, blockDataIndexer.fromIndex(i).getAsString()));
        }
    }

    public static class InputNumberGui extends ExpressionInventoryGui
    {
        private static final String GUI = ChatColor.WHITE + "\uF808" + "\u021E";

        private String input = "";
        private Result result = null;

        public InputNumberGui(@Nullable Consumer<IResult> onFinish)
        {
            super(new CustomInventory(9*4, GUI, Arrays.asList(0, 1, 2, 9, 10, 11, 18, 19, 20, 27, 28, 29, 31, 32, 34, 35)), onFinish);

            this.setButton(0, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "7"; this.updateTitle(); } ));
            this.setButton(1, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "8"; this.updateTitle(); } ));
            this.setButton(2, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "9"; this.updateTitle(); } ));
            this.setButton(3, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "4"; this.updateTitle(); } ));
            this.setButton(4, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "5"; this.updateTitle(); } ));
            this.setButton(5, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "6"; this.updateTitle(); } ));
            this.setButton(6, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "1"; this.updateTitle(); } ));
            this.setButton(7, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "2"; this.updateTitle(); } ));
            this.setButton(8, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "3"; this.updateTitle(); } ));
            this.setButton(10, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "0"; this.updateTitle(); } ));
            this.setButton(9, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> {
                if (this.input.length() > 0)
                {
                    this.input = this.input.substring(0, this.input.length() - 1);
                    this.updateTitle();
                }
            } ));
            this.setButton(11, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> { this.input += "."; this.updateTitle(); } ));

            this.setButton(12, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> {
                this.result = null;
                this.done();
            }));
            this.setButton(13, this.getButton(12));

            this.setButton(14, new GuiLambdaButton(new ItemStack(Material.AIR), interactData -> {
                try
                {
                    this.result = new Result(Double.parseDouble(this.input));
                    this.done();
                }
                catch (Exception ignored) { }
            }));
            this.setButton(15, this.getButton(14));
        }

        private void updateTitle()
        {
            StringBuilder title = new StringBuilder(GUI);
            title.append("\uF80B\uF80A\uF804");
            for (int i = 0; i < this.input.length(); ++i)
            {
                char c = this.input.charAt(i);
                if (c >= '0' && c <= '9')
                {
                    title.append((char)(0xc10e + (c - '0')));
                }
                else
                {
                    title.append((char) 0xc118);
                }
            }
            this.getMatterInventory().rename(title.toString());
        }

        @Override
        protected IResult getResult()
        {
            return this.result;
        }

        public static class Result implements ExpressionInventoryGui.IResult
        {
            private final double value;

            public Result(double value)
            {
                this.value = value;
            }

            public double getValue()
            {
                return this.value;
            }
        }
    }

    @Subcommand("gui")
    @CommandPermission("matter.gui")
    public void test(Player sender, int title)
    {
        InputNumberGui inputNumberGui = new InputNumberGui(iResult -> {
            InputNumberGui.Result result = (InputNumberGui.Result) iResult;
            sender.sendMessage("You wrote: " + (result == null ? "null" : result.getValue()));
        });
        Matter.getInstance().getInventoryGuiListener().register(inputNumberGui);
        sender.openInventory(inputNumberGui.getMatterInventory().getInventory());
    }
}













