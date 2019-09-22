package de.rola.kata.family;

/**
 * @author Bernd Kursawe <bernd.kursawe@praxisit.de>
 * @since 21.09.2019
 */
public class TooMuchParentsException extends RuntimeException {
    public TooMuchParentsException(final String name) {
        super("Tried to add more than 2 parents for " + name);
    }
}
