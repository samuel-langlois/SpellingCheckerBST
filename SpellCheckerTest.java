package assign07;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*****
 * This tests the functionality of the the Spell Checker
 * 
 * @author Samuel Langlois and Noah Graff
 * 
 */

class SpellCheckerTest {
	
	SpellChecker nullDictionary,
				 theOneDictionary, 
				 theDictionary, 
				 goodLuckDictionary,
				 helloWorldDictionary;
	@BeforeEach
	void setUp() {
		nullDictionary = new SpellChecker();
		theOneDictionary = new SpellChecker();
		theDictionary = new SpellChecker(new File("src/assign07/dictionary.txt"));
		goodLuckDictionary = new SpellChecker(new File("src/assign07/good_luck.txt"));
		helloWorldDictionary = new SpellChecker(new File("src/assign07/hello_world.txt"));
	}

	@Test
	void helloWorldTest() {
//		ArrayList<String> list = new ArrayList<String>();
		String[] list = {"Hello", "there", "world",  "Nice", "to", "meet", "you"};
		String[] missSpelled = {"helpo"};
		
//		assertEquals(0, helloWorldDictionary.spellCheck(new File("src/assign07/hello_world.txt")).toArray().length);
		helloWorldDictionary.writeDictionaryFile("Hello there, world.  Nice to meet you. helpo");
		
		assertEquals(missSpelled[0], helloWorldDictionary.spellCheck(new File("src/assign07/testDictionary.txt")).toArray()[0]);
	}

}
