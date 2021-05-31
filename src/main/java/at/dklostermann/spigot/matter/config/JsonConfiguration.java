package at.dklostermann.spigot.matter.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Map;

/**
 * This is the json equivalent to the YamlConfiguration.
 *
 * @see org.bukkit.configuration.file.YamlConfiguration
 */
public class JsonConfiguration extends FileConfiguration
{
    public JsonConfiguration()
    {

    }

    @Override
    @NotNull
    public String saveToString()
    {
        JSONObject jsonObject = new JSONObject(this.map);
        return jsonObject.toJSONString();
    }

    @Override
    public void loadFromString(@NotNull String s) throws InvalidConfigurationException
    {
        Map<? extends String, ?> input = null;
        try
        {
            JSONParser jsonParser = new JSONParser();
            Object jsonObject = jsonParser.parse(s);

            if (jsonObject instanceof JSONObject)
            {
                this.map.clear();
                //noinspection unchecked
                input = (Map<? extends String, ?>) jsonObject;
            }
            else
            {
                throw new InvalidConfigurationException("JsonConfiguration must have a json object as root. Json " +
                        "array is not supported.");
            }
        }
        catch (ParseException e)
        {
            throw new InvalidConfigurationException(e.getCause());
        }

        this.convertMapsToSections(input, this);
    }

    /**
     * This iterates over all elements of the input and converts any Map objects to ConfigurationSection.
     * @param input Input map, does not get altered.
     * @param section Current section
     */
    private void convertMapsToSections(@NotNull Map<? extends String, ?> input, @NotNull ConfigurationSection section)
    {
        for (Map.Entry<? extends String, ?> item : input.entrySet())
        {
            String key = ((Map.Entry<?, ?>) item).getKey().toString();
            Object value = item.getValue();
            if (value instanceof Map)
            {
                //noinspection unchecked
                this.convertMapsToSections((Map) value, section.createSection(key));
            }
            else
            {
                section.set(key, value);
            }
        }
    }

    @Override
    @NotNull
    protected String buildHeader()
    {
        // Json has no comments. Therefore no header.
        return "";
    }
}
