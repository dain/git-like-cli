package org.iq80.cli.config;

import com.google.common.collect.ImmutableMap;
import org.iq80.cli.Cli;
import org.iq80.cli.Command;
import org.iq80.cli.Option;
import org.testng.annotations.Test;

import java.util.concurrent.Callable;

import static junit.framework.Assert.assertEquals;

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
                               .withConfigurator(new MapConfigurator(ImmutableMap.of("thing.name", "configuration")))
                               .build();

        Callable action = cli.parse("thing");
        assertEquals(action.call(), "configuration");
    }

    @Test
    public void testCommandLineOverridesCondig() throws Exception
    {
        Cli<Callable> cli = Cli.<Callable>buildCli("thing", Callable.class).withCommand(Thing.class)
                               .withConfigurator(new MapConfigurator(ImmutableMap.of("thing.name", "configuration")))
                               .build();

        Callable action = cli.parse("thing", "--name", "argument");
        assertEquals(action.call(), "argument");
    }

    @Command(name="thing")
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
