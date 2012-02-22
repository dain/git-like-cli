package org.iq80.cli.config;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Throwables;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfiguration implements Configuration
{
    private final Properties properties;

    public PropertiesConfiguration(Properties properties)
    {
        Preconditions.checkArgument(properties != null, "may not pass null properties");
        this.properties = new Properties(properties);
    }

    public static Configuration fromProperties(File... sources) {
        Properties current = new Properties();
        for (File source : sources) {
            current = new Properties(current);
            if (source.exists()) {
                try {
                    FileInputStream in = new FileInputStream(source);
                    try {
                        current.load(in);
                    }
                    finally {
                        in.close();
                    }
                }
                catch (IOException e) {
                    throw Throwables.propagate(e);
                }
            }
        }
        return new PropertiesConfiguration(current);
    }

    @Override
    public Optional<String> lookup(String key)
    {
        return Optional.fromNullable(properties.getProperty(key));
    }
}
