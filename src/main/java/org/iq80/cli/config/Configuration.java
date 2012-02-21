package org.iq80.cli.config;

import com.google.common.base.Optional;

public interface Configuration
{
    public Optional<String> lookup(String key);
}
