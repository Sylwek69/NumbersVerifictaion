package pl.polsl.java.sylwek.lab4.model;



/**
 * Exceptional class, which is generated when entering the wrong number of
 * characters.
 *
 * @author Sylwester Iwanowski
 * @version 4.0
 */
public class IncorrectNumberOfCharactersException extends Exception {

    /**
     * Constructor without parametric
     */
    public IncorrectNumberOfCharactersException() {
    }

    /**
     * Constructor
     *
     * @param message display message
     */
    public IncorrectNumberOfCharactersException(String message) {
        super(message);
    }

    /**
     * @return when the real exception shows the message
     */
    @Override
    public String getMessage() {
        return "Podales nieprawidlową ilosc znakow\n";
    }

}
