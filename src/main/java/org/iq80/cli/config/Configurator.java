package org.iq80.cli.config;

import com.google.common.base.Optional;

public interface Configurator
{
    public Optional<String> lookup(String key);
}
