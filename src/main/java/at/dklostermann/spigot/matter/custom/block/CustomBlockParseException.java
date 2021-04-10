package at.dklostermann.spigot.matter.custom.block;

public class CustomBlockParseException extends Exception
{
    public CustomBlockParseException()
    {
    }

    public CustomBlockParseException(String s)
    {
        super(s);
    }

    public CustomBlockParseException(String s, Throwable throwable)
    {
        super(s, throwable);
    }

    public CustomBlockParseException(Throwable throwable)
    {
        super(throwable);
    }

    public CustomBlockParseException(String s, Throwable throwable, boolean b, boolean b1)
    {
        super(s, throwable, b, b1);
    }
}
