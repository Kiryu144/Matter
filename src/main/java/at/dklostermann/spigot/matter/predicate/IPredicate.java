package at.dklostermann.spigot.matter.predicate;

import org.jetbrains.annotations.Nullable;

/**
 * Interface for a generic predicate taking in a specific type.
 * @param <Target> Type to check.
 */
public interface IPredicate<Target>
{
    boolean isTrue(@Nullable Target target);
}
