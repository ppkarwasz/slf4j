package org.slf4j.jul.manager.spi;

import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * A factory for both types of OpenJDK loggers:
 * <ul>
 *     <li>{@link Logger}s available since Java 1.4.</li>
 *     <li>{@link System.Logger}s introduced in Java 9.</li>
 * </ul>
 * <p>
 *     Implementations of this class should be registered with {@link java.util.ServiceLoader} as both providing
 *     {@link System.LoggerFinder} and {@link LoggerFactory}.
 * </p>
 */
public abstract class LoggerFactory extends System.LoggerFinder {

    /**
     * Creates or returns a previously created Java 1.4 logger.
     *
     * @param name The name of the logger or {@code null} for an anonymous logger.
     * @return A logger, never {@code null}.
     */
    public abstract Logger getLogger(String name);

    @Override
    public abstract System.Logger getLogger(String name, Module module);

    /**
     * Returns the list of known Java 1.4 logger names.
     *
     * @return A list of logger names, never {@code null}.
     */
    public abstract Enumeration<String> getLoggerNames();
}
