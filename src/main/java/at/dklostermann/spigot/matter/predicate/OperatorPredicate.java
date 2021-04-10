package at.dklostermann.spigot.matter.predicate;


import at.dklostermann.spigot.matter.config.IConfigParseable;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;

/**
 * An abstract predicate implementation
 * @param <Target>
 */
public abstract class OperatorPredicate<Target> implements IPredicate<Target>, IConfigParseable
{
    private final Target target;
    private Operator operator = Operator.EQUAL;
    private Object value = null;

    public OperatorPredicate(@Nullable Target target)
    {
        this.target = target;
    }

    public Target getTarget()
    {
        return this.target;
    }

    @Nonnull
    public Operator getOperator()
    {
        return this.operator;
    }

    public Object getValue()
    {
        return this.value;
    }

    @Override
    public void parseFromConfig(@Nullable ConfigurationSection configurationSection)
    {
        if(configurationSection == null)
        {
            return;
        }

        this.value = configurationSection.get("value");

        String operatorString = configurationSection.getString("operator", null);
        if (operatorString == null)
        {
            return;
        }

        try
        {
            this.operator = Operator.valueOf(operatorString.toUpperCase());
        }
        catch (IllegalArgumentException illegalArgumentException)
        {
            throw new IllegalArgumentException(String.format("Unknown operator %s. Valid values are %s", operatorString,
                    Arrays.toString(Operator.values())));
        }
    }
}

















