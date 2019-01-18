package pl.polsl.java.sylwek.lab4.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NIP;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NO_NIP;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NO_PESEL;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NO_REGON;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.PESEL;

/**
 * The class represents whether the number is a pesel or a nip or a regon
 *
 * @author Sylwester Iwanowski
 * @version 4.0
 */
public class PeselNipRegonModel {

    /**
     * Classifies string to different classes of abstraction
     */
    public enum Value {
        PESEL, NO_PESEL, REGON, NO_REGON, NIP, NO_NIP
    }

    /**
     * pesel design - number of numbers
     */
    private final String PESEL_REGEX = "^\\d{11}$";

    /**
     * pesel regon - number of numbers
     */
    private final String REGON_REGEX = "^\\d{9}$";

    /**
     * nip design - number of numbers
     */
    private final String NIP_REGEX = "^\\d{10}$";

    /**
     * A list of all arguments given on the command line cleared of redundant
     * characters
     */
    private List<String> numbersHistory;

    /**
     * the value of the number (pesel, nip or regon)
     */
    private String value;

    /**
     * Constructor with a string argument
     *
     * @param value - tring parameter representing number which will be
     * classified by the model as pesel, nip, regon or nothing
     */
    public PeselNipRegonModel(String value) {
        this.value = value;
        numbersHistory = new ArrayList<>();
    }

    /**
     * Change model value to new value
     *
     * @param newValue - new string parameter provided by the user
     */
    public void changeValue(String newValue) {
        if (value != null) {
            numbersHistory.add(value);
        }
        value = newValue;
    }

    /**
     * Get full history of values including current value
     *
     * @return result - current list of strings
     */
    public List<String> getFullHistory() {
        List<String> result = new ArrayList(numbersHistory);
        result.add(value);
        return result;
    }

    /**
     * Cleaning method. Gets rid of unwanted characters
     *
     * @param dirtyValue - parameter with unwanted characters
     * @return value - parameter with unneeded characters removed
     */
    private String clearValue(String dirtyValue) {
        int[] numberArray = dirtyValue.codePoints()
                .filter(c -> c != '-')
                .filter(c -> c != ',')
                .filter(c -> c != ';')
                .filter(c -> c != '.')
                .toArray();
        return value = new String(numberArray, 0, numberArray.length);

    }

    /**
     * the method recognizes what number is given and throws exception when the
     * number is too short or too long
     *
     * @throws IncorrectNumberOfCharactersException
     */
    public Value numberingCheckValue() throws IncorrectNumberOfCharactersException {
        if (value == null) {
            throw new IncorrectNumberOfCharactersException();
        }

        value = clearValue(value);

        if ((value == null) || (value.length() < 9) || (value.length() > 11)) {
            throw new IncorrectNumberOfCharactersException();

        } else if (value.length() == 11) {
            return isPesel() ? PESEL : NO_PESEL;
        } else if (value.length() == 9) {
            return isRegon() ? Value.REGON : NO_REGON;
        } else {
            return isNip() ? NIP : NO_NIP;
        }
    }

    /**
     * Check if the number is pesel
     *
     * @return true if string is pesel
     */
    private boolean isPesel() {
        if (value == null) {
            return false;
        }
        if (!value.matches(PESEL_REGEX)) {
            return false;
        }
        int month = selectedNumber(2, 3, value);
        int day = selectedNumber(4, 5, value);
        if (month >= 13 || month == 0) {
            return false;
        }
        if (day == 0) {
            return false;
        }
        if ((month % 2 == 1 && day > 31) || (month % 2 == 0 && day > 30)) {
            return false;
        }
        int controlSum = controlSum(Arrays.asList(9, 7, 3, 1, 9, 7, 3, 1, 9, 7), value) % 10;
        if (controlSum != selectedNumber(10, value)) {
            return false;
        }
        return true;
    }

    /**
     * Check if the number is regon
     *
     * @return true if string is regon
     */
    private boolean isRegon() {

        if (!value.matches(REGON_REGEX)) {
            return false;
        }
        int woj = selectedNumber(0, 1, value);
        if (woj > 97 || (woj != 4 && woj != 34 && woj % 2 == 0)) {

            return false;
        }

        List<Integer> weighting = Arrays.asList(8, 9, 2, 3, 4, 5, 6, 7);
        int controlSum = controlSum(weighting, value) % 11;
        if (controlSum != selectedNumber(8, value)) {

            return false;
        }

        return true;
    }

    /**
     * Check if the number is nip
     *
     * @return true if string is nip
     */
    private boolean isNip() {

        if (!value.matches(NIP_REGEX)) {
            return false;
        }

        List<Integer> weighting = Arrays.asList(6, 5, 7, 2, 3, 4, 5, 6, 7);
        int controlSum = controlSum(weighting, value) % 11;
        if (controlSum != selectedNumber(9, value)) {
            return false;
        }
        return true;
    }

    /**
     * The function calculates the checksum
     *
     * @return counter 
     */
    private int controlSum(List<Integer> weighting, String value) {
        int counter = 0;
        for (int i = 0; i < weighting.size(); i++) {
            counter += weighting.get(i) * selectedNumber(i, value);
        }
        return counter;
    }

    /**
     * The functions gets n-th digit of the number
     *
     * @return N-th digit od the number
     */
    private int selectedNumber(int i, String value) {
        return Character.getNumericValue(value.charAt(i));
    }

    /**
     * Function returns subNumber from number
     *
     * @return returns number
     */
    private int selectedNumber(int i, int j, String value) {
        int result = 0;
        for (int g = i; g <= j; g++) {
            result *= 10;
            result += selectedNumber(g, value);
        }
        return result;
    }

    /**
     * the method of returning the value of the number
     *
     * @return value - returns value
     */
    public String getValue() {
        return value;
    }

    /**
     * A method that returns the value of all numbers given in the command line
     *
     * @return Returns numbersHistory
     */
    public List<String> getNumbersHistory() {
        return numbersHistory;
    }
}
