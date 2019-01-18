package pl.polsl.java.sylwek.lab4.model;


import org.junit.Test;

import static org.junit.Assert.*;

import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NIP;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NO_NIP;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NO_PESEL;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.NO_REGON;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.PESEL;
import static pl.polsl.java.sylwek.lab4.model.PeselNipRegonModel.Value.REGON;

/**
 * Unit tests for PeselNipRegonModel objects
 * @author Sylwester Iwanowski
 * @version 4.0
 */
public class PeselNipRegonModelTest {
    
    /**
     * Null object test for the method numberingCheckValue
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test(expected = IncorrectNumberOfCharactersException.class)
    public void testNull() throws IncorrectNumberOfCharactersException {
       PeselNipRegonModel model = new PeselNipRegonModel(null);
       model.numberingCheckValue();
    }

    /**
     * Metod should classify string as valid pesel
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test
    public void testValidPesel() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("92040602331");
        assertEquals(model.numberingCheckValue(), PESEL);
    }

    /**
     * Metod should classify string as invalid pesel
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test
    public void testInvalidPesel() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("92040602330");
        assertEquals(model.numberingCheckValue(), NO_PESEL);
    }
    
    /**
     * Metod should classify string as valid nip
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test
    public void testValidNip() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("625-10-54-462");
        assertEquals(model.numberingCheckValue(), NIP);
    }
    
    /**
     * Metod should classify string as invalid nip
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test
    public void testInvalidNip() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("625-10-54-463");
        assertEquals(model.numberingCheckValue(), NO_NIP);
        
    }
    
    /**
     * Metod should classify string as valid regon
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test
    public void testValidRegon() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("272918230");
        assertEquals(model.numberingCheckValue(), REGON);
    }
    
    /**
     * Metod should classify string as invalid regon
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test
    public void testInvalidRegon() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("272918231");
        assertEquals(model.numberingCheckValue(), NO_REGON);
        
    }

    /**
     * Check too long number
     * @throws IncorrectNumberOfCharactersException 
     */
    @Test (expected = IncorrectNumberOfCharactersException.class)
    public void testTooLongNumber() throws IncorrectNumberOfCharactersException{
        PeselNipRegonModel model = new PeselNipRegonModel("920406023311233123");
        model.numberingCheckValue();
    }

    /**
     * Test too short number
     * @throws Exception 
     */
    @Test(expected = IncorrectNumberOfCharactersException.class)
    public void testnumberingCheckValueTooShouldNumber() throws Exception {
        PeselNipRegonModel model = new PeselNipRegonModel("92");
        model.numberingCheckValue();
    }

    /**
     * Check empty number
     * @throws Exception 
     */
    @Test(expected = IncorrectNumberOfCharactersException.class)
    public void testnumberingCheckValueEmptyNumber() throws Exception {
        PeselNipRegonModel model = new PeselNipRegonModel("");
        model.numberingCheckValue();
    }
  
}