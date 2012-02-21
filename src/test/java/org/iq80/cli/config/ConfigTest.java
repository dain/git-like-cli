package org.iq80.cli.config;

import com.google.common.collect.ImmutableMap;
import org.iq80.cli.Cli;
import org.iq80.cli.Command;
import org.iq80.cli.Option;
import org.iq80.cli.OptionType;
import org.testng.annotations.Test;

import java.io.File;
import java.util.concurrent.Callable;

import static org.iq80.cli.config.PropertiesConfiguration.fromProperties;
import static org.testng.AssertJUnit.assertEquals;

public class ConfigTest
{
    @Test
    public void testBaseCase() throws Exception
    {
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .build();

        Callable action = cli.parse("thing", "--name", "argument");
        assertEquals(action.call(), "argument");
    }

    @Test
    public void testFromConfig() throws Exception
    {
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .withConfiguration(new MapConfiguration(ImmutableMap.of("thing.name", "configuration")))
                               .build();

        Callable action = cli.parse("thing");
        assertEquals(action.call(), "configuration");
    }

    @Test
    public void testCommandLineOverridesConfig() throws Exception
    {
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .withConfiguration(new MapConfiguration(ImmutableMap.of("thing.name", "configuration")))
                               .build();

        Callable action = cli.parse("thing", "--name", "argument");
        assertEquals(action.call(), "argument");
    }

    @Test
    public void testPropertiesConfiguration() throws Exception
    {
        File first = new File("src/test/resources/org/iq80/cli/config/first.properties");
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .withConfiguration(fromProperties(first))
                               .build();

        Callable action = cli.parse("thing");
        assertEquals(action.call(), "from first");
    }

    @Test
    public void testPropertiesConfigurationTwoFiles() throws Exception
    {
        File first = new File("src/test/resources/org/iq80/cli/config/first.properties");
        File second = new File("src/test/resources/org/iq80/cli/config/second.properties");
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .withConfiguration(fromProperties(first, second))
                               .build();

        Callable action = cli.parse("thing");
        assertEquals(action.call(), "from second");
    }

    @Test
    public void testPropertiesConfigurationThreeFiles() throws Exception
    {
        File zeroth = new File("src/test/resources/org/iq80/cli/config/zeroth.properties");
        File first = new File("src/test/resources/org/iq80/cli/config/first.properties");
        File second = new File("src/test/resources/org/iq80/cli/config/second.properties");
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .withConfiguration(fromProperties(first, second, zeroth))
                               .build();

        Callable action = cli.parse("thing");
        assertEquals(action.call(), "from second");
    }

    @Command(name = "thing")
    public static class Thing implements Callable<String>
    {
        @Option(name = "--name", configuration = "thing.name")
        public String name;

        @Override
        public String call() throws Exception
        {
            return name;
        }
    }
}
