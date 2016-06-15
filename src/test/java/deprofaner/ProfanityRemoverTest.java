/**
 * 
 */
package deprofaner;

import static org.junit.Assert.assertEquals;

import org.apache.hadoop.io.Text;
import org.junit.Test;

import com.dataflowdeveloper.deprofaner.ProfanityRemover;

/**
 * @author tspann
 * @PaasDev
 *
 */
public class ProfanityRemoverTest {

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
