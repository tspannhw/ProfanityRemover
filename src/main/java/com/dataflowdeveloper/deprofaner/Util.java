package com.dataflowdeveloper.deprofaner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Strings;

/**
 * utility
 * 
 * @author tspann
 * 
 * we really need some complex NLP here, since some word fragments that are profanities are not in other words.
 * 
 * This API is decent but not free https://www.webpurify.com/documentation/methods/replace/
 *
 */
public final class Util {

	private static final Logger LOG = Logger.getLogger(Util.class);

	static {
		Util.loadFilter();
	}

	/**
	 * profanity file
	 */
	private final static String termList = "/terms.txt";

	/**
	 * create a directory
	 */
	private final static String termListInstalled = "/opt/demo/udf/terms.txt";

	/**
	 * Default filler character
	 */
	private final static String defaultFiller = "*";

	/**
	 * Stars
	 */
	private static String[] replacementList = null;

	/**
	 * Profanities
	 */
	private static String[] profanityList = null;

	/**
	 * 
	 * @param number
	 * @return
	 */
	public static String fillWithCharacter(int number) {
		return Strings.repeat(defaultFiller, number);
	}

	/**
	 * 
	 * @param number
	 * @param filler
	 * @return
	 */
	public static String fillWithCharacter(int number, String filler) {
		return Strings.repeat(filler, number);
	}

	/**
	 * load profanities to filter
	 */
	public static void loadFilter() {
		if (profanityList != null && profanityList.length > 0) {
			return;
		}
		InputStream is = null;
		BufferedReader in = null;

		List<String> profanities = new ArrayList<String>();
		List<String> replacements = new ArrayList<String>();

		try {

			File termFile = null;

			try {
				termFile = FileUtils.toFile(Util.class.getResource(termList));

				if (termFile == null) {
					termFile = FileUtils.getFile(termListInstalled);
				}
			} catch (Exception e) {
				LOG.error("Exception reading term file." + e.getLocalizedMessage());
			}

			List<String> lines = FileUtils.readLines(termFile, "UTF-8");

			// so ugly, but good enough for example
			// @Todo   Refactor me
			for (String line : lines) {
				if (!Strings.isNullOrEmpty(line)) {
					String originalLine = line;
					line = originalLine + " "; // only want words not fragments
					profanities.add(line);
					replacements.add(fillWithCharacter(originalLine.length()) );
					profanities.add(line.toLowerCase());
					replacements.add(fillWithCharacter(originalLine.length()) );
					profanities.add(line.toUpperCase());
					replacements.add(fillWithCharacter(originalLine.length()) );
					profanities.add(StringUtils.capitalize(line.trim()));
					replacements.add(fillWithCharacter(originalLine.length()) );
					
					line = " " + originalLine; // only want words not fragments
					profanities.add(line);
					replacements.add(" " + fillWithCharacter(originalLine.length()));
					profanities.add(line.toLowerCase());
					replacements.add(" " + fillWithCharacter(originalLine.length()));
					profanities.add(line.toUpperCase());
					replacements.add(" " + fillWithCharacter(originalLine.length()));
					profanities.add(StringUtils.capitalize(line.trim()));
					replacements.add(" " + fillWithCharacter(originalLine.length()));
				}
			}

			replacementList = replacements.toArray(new String[replacements.size()]);
			profanityList = profanities.toArray(new String[profanities.size()]);
		} catch (Exception t) {
			LOG.error("Load Filter error: ", t);
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
				LOG.error("Load Filter IO error: ", e);
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
	public static String filterOutProfanity(String term) {
		if (Strings.isNullOrEmpty(term)) {
			return "";
		}
		if (profanityList == null || replacementList == null || profanityList.length == 0
				|| replacementList.length == 0) {
			Util.loadFilter();
		}

		String cleaned = null;
		try {
			cleaned = StringUtils.replaceEach(term, profanityList, replacementList);
		} catch (Exception e) {
			LOG.error("Failed to replace term");
			cleaned = term;
		}
		return cleaned;
	}
}
