package at.dklostermann.spigot.matter.custom.font;

public class NegativeSpacesFont
{
    private static final char[] NEGATIVE_OFFSETS = { '\uF801', '\uF802', '\uF804', '\uF808', '\uF809', '\uF80A', '\uF80B', '\uF80C', '\uF80D', '\uF80E', '\uF80F' };
    private static final char[] POSITIVE_OFFSETS = { '\uF821', '\uF822', '\uF824', '\uF828', '\uF829', '\uF82A', '\uF82B', '\uF82C', '\uF82D', '\uF82E', '\uF82F' };
    private static final int[] CHARACTER_WIDTHS = { 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 0, 3, 3, 3, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 2, 2, 4, 6, 6, 6, 6, 2, 5, 5, 5, 6, 2, 6, 2, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
            2, 2, 5, 6, 5, 6, 7, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 4, 6, 4,
            6, 6, 3, 6, 6, 6, 6, 6, 5, 6, 6, 2, 6, 5, 3, 6, 6, 6, 6, 6, 6, 6, 4, 6, 6, 6, 6, 6, 6, 5, 2, 5, 7, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 5, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 2, 3, 3, 3, 6,
            3, 3, 5, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 7, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 6, 11, 3, 3, 3, 3, 3, 3, 3,
            3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 7, 3, 3, 3, 3, 3, 3, 3
    };
    private static final int[] CHARACTER_OFFSETS = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, -1, -1, -1, -1, 6, -1, 7,
            -1, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, -1, -1, -1, 19, -1, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
            30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, -1, 53, 54, 55,
            56, 57, -1, 58, 59, 60, 61, -1, -1, 62, 63, 64, 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, -1, -1, -1, 76,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
            -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1
    };

    public static String CalculateCustomFont(String text, int start)
    {
        StringBuilder ss = new StringBuilder();

        for (int i = 0; i < text.length(); ++i)
        {
            if (text.charAt(i) == ' ')
            {
                ss.append('\uF824');
            }
            else
            {
                int offset = CHARACTER_OFFSETS[text.charAt(i)];
                if (offset >= 0)
                {
                    ss.append(Character.valueOf((char) (start + offset)));
                }
            }
        }

        return ss.toString();
    }

    public static int CalculateOffset(String text)
    {
        int sum = 0;
        for (int i = 0; i < text.length(); ++i)
        {
            sum += CHARACTER_WIDTHS[Math.min(254, text.charAt(i))];
        }
        return sum;
    }

    public static String CalculateOffset(int pixels)
    {
        final char[] offsets = pixels < 0 ? NEGATIVE_OFFSETS : POSITIVE_OFFSETS;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < offsets.length; ++i)
        {
            if ((pixels & (1 << i)) != 1)
            {
                str.append(offsets[i]);
            }
        }

        return str.toString();
    }
}
