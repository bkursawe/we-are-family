package de.rola.kata.family;

/**
 * @author Bernd Kursawe <bernd.kursawe@praxisit.de>
 * @since 19.09.2019
 */
public enum Gender {
    Unknown, Male, Female;

    public boolean isAllowed(Gender other) {
        if (this == Unknown) return true;
        if (this == other) return true;
        return false;
    }
}
