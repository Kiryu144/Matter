package at.dklostermann.spigot.matter.blockdata;

import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.NoteBlock;
import org.jetbrains.annotations.NotNull;

public class NoteBlockDataIndexer extends CachedBlockDataIndexer
{
    private final Material material;

    /**
     * This constructor creates an invalid state. To create a valid state, use createForIndex().
     */
    public NoteBlockDataIndexer()
    {
        this.material = null;
    }

    private NoteBlockDataIndexer(Material material)
    {
        this.material = material;
    }

    @Override
    public int getIndex(@NotNull BlockData blockData)
    {
        NoteBlock noteBlock = (NoteBlock) blockData;
        int index = 0;
        index |= noteBlock.getNote().getId();
        index <<= 4;
        index |= noteBlock.getInstrument().ordinal();
        index <<= 1;
        index |= noteBlock.isPowered() ? 1 : 0;
        return index;
    }

    @Override
    public int max()
    {
        return 800;
    }

    @NotNull
    @Override
    public Class<? extends BlockData> getBlockDataClass()
    {
        return NoteBlock.class;
    }

    @NotNull
    @Override
    public IBlockDataIndexer createNew(@NotNull Material material)
    {
        return new NoteBlockDataIndexer(material);
    }

    @NotNull
    @Override
    BlockData createForIndex(int index)
    {
        // 00000000 00000000 000000AA AAABBBBC -> Layout of index. A: noteID, B: instrument, C: powered
        NoteBlock noteBlock = (NoteBlock) this.material.createBlockData();
        noteBlock.setPowered((index & 1) != 0);
        index >>= 1;
        noteBlock.setInstrument(Instrument.getByType((byte) (index & 15)));
        index >>= 4;
        noteBlock.setNote(new Note(index & 31));
        return noteBlock;
    }
}
