package org.iq80.cli.config;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class MapConfiguration implements Configuration
{
    private final Map<String, String> config;

    public <T extends Map<String, String>> MapConfiguration(T config) {
        this.config = ImmutableMap.copyOf(config);
    }

    @Override
    public Optional<String> lookup(String key)
    {
        return Optional.fromNullable(config.get(key));
    }
}
