package net.andrasia.spigot.core.predicate;

import javax.annotation.Nullable;

/**
 * Interface for a generic predicate taking in a specific type.
 * @param <Target> Type to check.
 */
public interface IPredicate<Target>
{
    boolean isTrue(@Nullable Target target);
}
