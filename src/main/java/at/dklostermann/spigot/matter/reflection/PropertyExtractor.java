package at.dklostermann.spigot.matter.reflection;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class used reflection to find all getter of a given object type and stores the Method instances with
 * a given unique name. The unique name depends on the getter name. For example:
 * getCurrentAmount() -> current_amount
 * getHealth() -> health
 *
 * @param <T> Object type to extract the properties.
 */
public class PropertyExtractor<T>
{
    private final Map<String, Method> properties = new HashMap<>();
    private final Class<T> propertyHolder;

    /**
     * @param propertyHolder Class of object to extract properties.
     */
    public PropertyExtractor(Class<T> propertyHolder)
    {
        this.propertyHolder = propertyHolder;
    }

    /**
     * Converts a given pascal case word to snake case.
     * @param string Valid PascalCase string
     * @return All lowercase snake case string
     */
    private static String PascalCaseToSnakeCase(@NotNull String string)
    {
        StringBuilder ss = new StringBuilder();
        for (int i = 0; i < string.length(); ++i)
        {
            char c = string.charAt(i);
            if (Character.isUpperCase(c))
            {
                c = Character.toLowerCase(c);
                if (i > 0)
                {
                    ss.append('_');
                }
            }
            ss.append(c);
        }
        return ss.toString();
    }

    /**
     * Reflection. Finds all methods that have the following signature 'Number getXXX()'.
     */
    private void populatePlayerProperties()
    {
        for (Method method : this.propertyHolder.getMethods())
        {
            String getterPrefix = "get";
            if (!method.getName().startsWith(getterPrefix) || !method.isAccessible() || method.getParameterCount() > 0 || method.getReturnType() == void.class)
            {
                continue;
            }
            String methodNameWithoutPrefix = method.getName().substring(getterPrefix.length());
            String propertyName = PascalCaseToSnakeCase(methodNameWithoutPrefix);
            this.properties.put(propertyName, method);
        }
    }

    /**
     * @param property Property name in pascal_case.
     * @return Returns method for given name. Null if not found.
     */
    @Nullable
    public Method getMethod(@NotNull String property)
    {
        return this.properties.get(property);
    }

    /**
     * @param property Property name in lowercase snake_case.
     * @return Value of the property.
     */
    @Nullable
    public Object getProperty(@NotNull T target, @NotNull String property)
    {
        Method method = this.getMethod(property);
        try
        {
            return method != null ? method.invoke(target) : null;
        }
        catch (IllegalAccessException | InvocationTargetException e)
        {
            // Exceptions normally cannot occur, as we already check most stuff in populatePlayerProperties()
            e.printStackTrace();
            return null;
        }
    }

    /**
     * @return Returns a set of all known property names.
     */
    @NotNull
    public Set<String> getProperties()
    {
        return this.properties.keySet();
    }
}
