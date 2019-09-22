package de.rola.kata.family;

/**
 * @author Bernd Kursawe <bernd.kursawe@praxisit.de>
 * @since 19.09.2019
 */
public enum Gender {
    UNKNOWN, MALE, FEMALE;

    public boolean isAllowed(Gender other) {
        if (this == UNKNOWN) return true;
        return this == other;
    }

    public Gender other() {
        switch (this) {
            case MALE:
                return FEMALE;
            case FEMALE:
                return MALE;
            default:
                return UNKNOWN;
        }
    }
}
