package net.andrasia.spigot.core.test;

import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a container for testcases. It is also able to run them all and print the results.
 * @see Test
 */
public class TestRunner
{
    private final Plugin plugin;
    private final List<Test> testList = new ArrayList<>();

    public TestRunner(@Nonnull Plugin plugin)
    {
        this.plugin = plugin;
    }

    public void addTest(@Nonnull Test test)
    {
        this.testList.add(test);
    }

    /**
     * Runs all tests and print their result.
     * @return True when all tests succeed.
     */
    public boolean runAllTests()
    {
        int successCount = 0;
        for (Test test : this.testList)
        {
            this.plugin.getLogger().info(String.format("Starting test '%s'", test.getName()));
            try
            {
                long timeNeeded = test.run();
                ++successCount;
                this.plugin.getLogger().info(String.format("Test '%s' [SUCCESS] (%dms)", test.getName(), timeNeeded));
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                this.plugin.getLogger().info(String.format("Test '%s' [FAILURE]", test.getName()));
            }
        }

        System.out.println(String.format("%d/%d tests succeded", successCount, this.testList.size()));
        return successCount == this.testList.size();
    }
}
