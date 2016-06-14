/**
 * 
 */
package deprofaner;

import static org.junit.Assert.*;

import org.junit.Test;

import com.dataflowdeveloper.deprofaner.ProfanityRemover;
import org.apache.hadoop.io.Text;

/**
 * @author tspann
 * @PaasDev
 *
 */
public class ProfanityRemoverTest {

	/** test string **/
	
	private static final String profaneText = "Ass is a kind of donkey";
	
	/**
	 * Test method for {@link com.dataflowdeveloper.deprofaner.ProfanityRemover#fillWithCharacter(int, java.lang.String)}.
	 */
	@Test
	public void testFillWithCharacterIntString() {
		ProfanityRemover remover = new ProfanityRemover();
		
		assertEquals("XXXXX", remover.fillWithCharacter(5, "X") );				
	}

	/**
	 * Test method for {@link com.dataflowdeveloper.deprofaner.ProfanityRemover#filterOutProfanity(java.lang.String)}.
	 */
	@Test
	public void testFilterOutProfanity() {
		ProfanityRemover remover = new ProfanityRemover();
		assertEquals("*** is a kind of donkey", 
				remover.filterOutProfanity(profaneText));
	}

	/**
	 * Test method for {@link com.dataflowdeveloper.deprofaner.ProfanityRemover#evaluate(org.apache.hadoop.io.Text)}.
	 */
	@Test
	public void testEvaluate() {
		ProfanityRemover remover = new ProfanityRemover();
		
		final String phraseUnderTest = "Bad words like ass, shit, stuff and more.  Not the good words.";
		Text cleanString = new Text("Bad words like ***, ****, stuff and more.  Not the good words.");
		Text testString = new Text(phraseUnderTest);
		Text newString = remover.evaluate(testString);
		assertEquals(cleanString, newString);
	}

}
