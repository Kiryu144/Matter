package net.andrasia.spigot.core.test;

import javax.annotation.Nonnull;

/**
 * This is the baseclass for any testcase. It basically consists of a name and 3 methods:
 * - Test#prepareTest -> Prepares any data needed for the test without influencing the test duration result.
 * - Test#executeTest -> Run the actual tests.
 * - Test#cleanupTest -> Cleanup of any generated testdata. This WILL be called, even if an exception occurs before.
 *
 * A test is considered a success, if no unhandled exception was generated.
 */
public abstract class Test
{
    private final String name;

    public Test(@Nonnull String name)
    {
        this.name = name;
    }

    @Nonnull
    public String getName()
    {
        return this.name;
    }

    /**
     * Runs the testcase. Throws an exception if the test failes.
     * @see Test
     * @return Returns the time in milliseconds the Test#executeTest() needed.
     * @throws Exception Only thrown if an error occurs
     */
    public long run() throws Exception
    {
        // Prepare test
        try
        {
            this.prepareTest();
        }
        catch (Exception exception)
        {
            this.cleanupTest();
            throw exception;
        }

        // Run test
        long timeNeeded = -1;
        try
        {
            long start = System.nanoTime();
            this.executeTest();
            timeNeeded = (System.currentTimeMillis() - start) / 1000000;
        }
        catch (Exception exception)
        {
            this.cleanupTest();
            throw exception;
        }

        // Cleanup test
        try
        {
            this.cleanupTest();
        }
        catch (Exception exception)
        {
            throw exception;
        }

        return timeNeeded;
    }

    public abstract void prepareTest() throws Exception;
    public abstract void executeTest() throws Exception;
    public abstract void cleanupTest() throws Exception;
}
