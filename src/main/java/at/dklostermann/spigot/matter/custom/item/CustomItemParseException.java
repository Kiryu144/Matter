package at.dklostermann.spigot.matter.custom.item;

public class CustomItemParseException extends RuntimeException
{
    public CustomItemParseException()
    {
    }

    public CustomItemParseException(String s)
    {
        super(s);
    }

    public CustomItemParseException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public CustomItemParseException(Throwable throwable)
    {
        super(throwable);
    }

    public CustomItemParseException(String s, Throwable throwable, boolean b, boolean b1)
    {
        super(s, throwable, b, b1);
    }
}
