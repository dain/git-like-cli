package org.iq80.cli.config;

import com.google.common.base.Optional;

public class NoOpConfigurator implements Configurator
{
    @Override
    public Optional<String> lookup(String key)
    {
        return Optional.absent();
    }
}
