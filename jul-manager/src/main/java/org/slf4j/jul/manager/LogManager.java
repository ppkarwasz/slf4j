package org.slf4j.jul.manager;

import org.slf4j.jul.manager.spi.LoggerFactory;

import java.util.Enumeration;
import java.util.List;
import java.util.ServiceLoader;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Simple universal implementation of {@link java.util.logging.LogManager} that delegates all functionality to
 * a logger factory loaded through {@link ServiceLoader}.
 */
public final class LogManager extends java.util.logging.LogManager {

    private final LoggerFactory loggerFactory;

    public LogManager() {
        ClassLoader loader = getClass().getClassLoader();
        List<LoggerFactory> adapters = ServiceLoader.load(LoggerFactory.class, loader).stream()
                .map(ServiceLoader.Provider::get)
                .collect(Collectors.toList());
        if (adapters.size() > 1) {
            StringBuilder sb = new StringBuilder("Multiple `LoggerFactory`s found:");
            adapters.forEach(a -> sb.append("\n\t").append(a.getClass()));
            sb.append("\nUsing the first one.");
            System.err.println(sb);
        }
        loggerFactory = adapters.isEmpty() ? null : adapters.get(0);
    }

    @Override
    public Logger getLogger(String name) {
        return loggerFactory != null ? loggerFactory.getLogger(name) : super.getLogger(name);
    }

    @Override
    public Enumeration<String> getLoggerNames() {
        return loggerFactory != null ? loggerFactory.getLoggerNames() : super.getLoggerNames();
    }
}
