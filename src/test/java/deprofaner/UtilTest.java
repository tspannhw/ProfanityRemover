/**
 * 
 */
package deprofaner;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.dataflowdeveloper.deprofaner.Util;

/**
 * @author tspann
 *
 */
public class UtilTest {

	private static final String profaneText = "Ass is a kind of donkey";
	
	/**
	 * Test method for {@link com.dataflowdeveloper.deprofaner.ProfanityRemover#fillWithCharacter(int, java.lang.String)}.
	 */
	@Test
	public void testFillWithCharacterIntString() {
		
		assertEquals("XXXXX", Util.fillWithCharacter(5, "X") );				
	}

	/**
	 * Test method for {@link com.dataflowdeveloper.deprofaner.ProfanityRemover#filterOutProfanity(java.lang.String)}.
	 */
	@Test
	public void testFilterOutProfanity() {
		assertEquals("*** is a kind of donkey", 
				Util.filterOutProfanity(profaneText));
	}
}
