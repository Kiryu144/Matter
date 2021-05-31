package at.dklostermann.spigot.matter.predicate;

import at.dklostermann.spigot.matter.reflection.PropertyExtractor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class OperatorPredicatePlayerBukkit extends OperatorPredicate<Player>
{
    private static final PropertyExtractor<Player> playerProperties = new PropertyExtractor<>(Player.class);
    private Method getterMethod = null;

    public OperatorPredicatePlayerBukkit(@Nullable Player target)
    {
        super(target);
    }

    @Override
    public void parseFromConfig(@Nullable ConfigurationSection configurationSection)
    {
        super.parseFromConfig(configurationSection);
        if (configurationSection == null)
        {
            return;
        }

        String propertyName = configurationSection.getString("property", null);
        if (propertyName == null)
        {
            throw new IllegalArgumentException("Property is missing.");
        }

        this.getterMethod = playerProperties.getMethod(propertyName);
        if (this.getterMethod == null)
        {
            throw new IllegalArgumentException(String.format("Unknown property '%s'. Possible values -> %s", propertyName, Arrays.toString(playerProperties.getProperties().toArray())));
        }
    }

    @Override
    public boolean isTrue(@Nullable Player player)
    {
        if (this.getterMethod == null)
        {
            return false;
        }

        try
        {
            return this.getOperator().check(this.getterMethod.invoke(player), this.getValue());
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            // This should normally never happen.
            e.printStackTrace();
            return false;
        }
    }
}
