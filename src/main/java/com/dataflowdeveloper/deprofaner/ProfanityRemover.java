/**
 * Created by tspann on 6/14/16.
 */

package com.dataflowdeveloper.deprofaner;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.io.Text;

/**
 * Profanity Filter for Hive
 * 
 * This is a UDF example
 * 
 * TODO:
 * Simple example, not designed for performance
 * Should handle more items and cache things.
 * Maybe store terms in a hive table
 * Should handle case insensitive and more spellings
 * Find online source of huge blacklist
 * Should handle other languages
 * 
 * @author tspann
 * @PaasDev
 * 
 */
@Description(
		  name = "profanityremover",
		  value = "_FUNC_(string) - sanitizes text by replacing profanities "
		)
public final class ProfanityRemover extends UDF {

	/**
	 * UDF Evaluation
	 * 
	 * @param s
	 *            Text passed in
	 * @return Text cleaned
	 */
	public Text evaluate(final Text s) {
		if (s == null) {
			return null;
		}
		String cleaned = Util.filterOutProfanity(s.toString());		
		return new Text(cleaned);
	}

}
