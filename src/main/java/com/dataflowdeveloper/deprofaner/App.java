/**
 * 
 */
package com.dataflowdeveloper.deprofaner;

/**
 * @author tspann
 *
 */
public class App {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
			
		String phraseUnderTest = null;
		
		if ( args.length >0 && args[0] != null ) {
			phraseUnderTest = args[0];
		}
		else {
			phraseUnderTest = "Bad words like Ass, shit, stuff and more.  Not the good words. Shit Spreader Ass Fuck Engineer";
		}
		System.out.println("Cleaned text:[" + Util.filterOutProfanity(phraseUnderTest) + "]");
	}

}
