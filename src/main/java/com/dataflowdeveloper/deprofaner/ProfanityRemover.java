/**
 * Created by tspann on 6/14/16.
 */

package com.dataflowdeveloper.deprofaner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hive.ql.exec.UDF;
import org.apache.hadoop.hive.ql.exec.UDFMethodResolver;
import org.apache.hadoop.io.Text;

import com.google.common.base.Strings;

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
public final class ProfanityRemover extends UDF {

	/**
	 * load the filter
	 */
	public ProfanityRemover() {
		super();
		loadFilter();
	}

	public ProfanityRemover(UDFMethodResolver rslv) {
		super(rslv);
		loadFilter();
	}

	/**
	 * profanity file
	 */
	private final static String termList = "/terms.txt";

	/**
	 * Default filler character
	 */
	private final static String defaultFiller = "*";

	/**
	 * Stars
	 */
	private String[] replacementList = null;

	/**
	 * Profanities
	 */
	private String[] profanityList = null;

	/**
	 * 
	 * @param number
	 * @return
	 */
	public String fillWithCharacter(int number) {
		return Strings.repeat(defaultFiller, number);
	}

	/**
	 * 
	 * @param number
	 * @param filler
	 * @return
	 */
	public String fillWithCharacter(int number, String filler) {
		return Strings.repeat(filler, number);
	}

	/**
	 * load profanities to filter
	 */
	public void loadFilter() {
		InputStream is = null;
		BufferedReader in = null;

		List<String> profanities = new ArrayList<String>();
		List<String> replacements = new ArrayList<String>();

		try {
			List<String> lines = FileUtils.readLines(FileUtils.toFile(getClass().getResource(termList)), "UTF-8");

			for (String line : lines) {
				if (!Strings.isNullOrEmpty(line)) {
					profanities.add(line.trim());
					replacements.add(fillWithCharacter(line.length()));
					profanities.add(line.trim().toLowerCase());
					replacements.add(fillWithCharacter(line.length()));
					profanities.add(line.trim().toUpperCase());
					replacements.add(fillWithCharacter(line.length()));
					profanities.add(StringUtils.capitalize(line.trim()));
					replacements.add(fillWithCharacter(line.length()));
				}
			}

			replacementList = replacements.toArray(new String[replacements.size()]);
			profanityList = profanities.toArray(new String[profanities.size()]);
		} catch (Throwable t) {
			t.printStackTrace();
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				in = null;

				if (is != null) {
					is.close();
				}
				is = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * clean out garbage
	 * 
	 * @param term
	 *            sent
	 * @return clean String
	 */
	public String filterOutProfanity(String term) {
		if (Strings.isNullOrEmpty(term) || profanityList == null || replacementList == null) {
			return "";
		}

		return StringUtils.replaceEach(term, profanityList, replacementList);
	}

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
		return new Text(filterOutProfanity(s.toString()));
	}

}
