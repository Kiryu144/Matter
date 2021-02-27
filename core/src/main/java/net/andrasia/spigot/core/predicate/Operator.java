package net.andrasia.spigot.core.predicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * This is a enum-implementation of the relational operators.
 */
public enum Operator
{
    SMALLER, // <
    SMALLER_OR_EQUAL, // <=
    EQUAL, // ==
    NOT_EQUAL, // !=
    BIGGER_OR_EQUAL, // >=
    BIGGER; // >

    /**
     * Compares two inputs using the operator used. If one of the two objects are null, their nullness will be matched.
     *
     * @param left Left side of the operator
     * @param right Right side of the operator
     * @return True if operation matches. False if not. False also if unknown types are provided.
     */
    boolean check(@Nullable Object left, @Nullable Object right)
    {
        if (left == null || right == null)
        {
            return this.equals(EQUAL) && left == right;
        }

        if (left instanceof Boolean)
        {
            left = ((boolean) left) ? 1 : 0;
        }

        if (right instanceof Boolean)
        {
            right = ((boolean) right) ? 1 : 0;
        }

        if (left instanceof Number)
        {
            return right instanceof Number && this.check(((Number) left).doubleValue(), ((Number) right).doubleValue());
        }

        return this.check(left.toString(), right.toString());
    }

    /**
     * @see Operator#check(Object, Object)
     */
    boolean check(double left, double right)
    {
        switch (this)
        {
            case SMALLER:
                return left < right;
            case SMALLER_OR_EQUAL:
                return left <= right;
            case EQUAL:
                return left == right;
            case NOT_EQUAL:
                return left != right;
            case BIGGER_OR_EQUAL:
                return left >= right;
            case BIGGER:
                return left > right;
            default:
                return false;
        }
    }

    /**
     * @see Operator#check(Object, Object)
     */
    boolean check(int left, int right)
    {
        switch (this)
        {
            case SMALLER:
                return left < right;
            case SMALLER_OR_EQUAL:
                return left <= right;
            case EQUAL:
                return left == right;
            case NOT_EQUAL:
                return left != right;
            case BIGGER_OR_EQUAL:
                return left >= right;
            case BIGGER:
                return left > right;
            default:
                return false;
        }
    }

    /**
     * @see Operator#check(Object, Object)
     */
    boolean check(boolean left, boolean right)
    {
        return this.check(left ? 1 : 0, right ? 1 : 0);
    }

    /**
     * @see Operator#check(Object, Object)
     */
    boolean check(@Nonnull String left, @Nonnull String right)
    {
        return this.equals(EQUAL) == left.equalsIgnoreCase(right);
    }
}
