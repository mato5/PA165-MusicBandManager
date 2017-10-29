package cz.muni.fi.pa165.enums;

/**
 * @author Iurii xkuznetc Kuznetcov
 * <p>
 * Hardcoded enum that represents a list of supported music genres for bands.
 */
public enum Genre {

    UNKNOWN,
    BLUES,
    COUNTRY,
    CLASSICAL,
    ELECTRONIC,
    FOLK,
    JAZZ,
    NEW_AGE,
    REGGAE,
    RAP,
    ROCK,
    R_B,
    MISC,
    INDUSTRIAL,
    WORLD;

    /**
     * Checks the enum for a string contained within
     *
     * @param valueString
     * @return true if the string is contained in the enum
     */
    public static boolean contains(final String valueString) {
        for (Genre genre : Genre.values()) {
            if (genre.name().equals(valueString)) {
                return true;
            }
        }
        return false;
    }
}

