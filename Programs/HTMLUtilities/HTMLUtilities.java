/**
 * Utilities for handling HTML, tokenizes tags, words, numbers, and punctuation.
 *
 * @author Aditya Dendukuri
 * @since November 1 2024
 */
public class HTMLUtilities {

	/**
	 * Break the HTML string into tokens. The array returned is
	 * exactly the size of the number of tokens in the HTML string.
	 * Example: HTML string = "Goodnight moon goodnight stars"
	 * returns { "Goodnight", "moon", "goodnight", "stars" }
	 * 
	 * @param str the HTML string
	 * @return the String array of tokens
	 */
	public String[] tokenizeHTMLString(String str) {
		// Make the size of the array large to start
		String[] result = new String[10000];
		int tokenIndex = 0;
		String currentToken = "";
		boolean insideTag = false;
		boolean hyphenUsed = false;
		boolean insideNumber = false;
		boolean numberNegative = false;
		boolean exponent = false;
		boolean decimalPoint = false;

		for (int i = 0; i < str.length(); i++) {
			char currentChar = str.charAt(i);

			if (insideTag) {
				currentToken += currentChar;
				if (currentChar == '>') {
					result[tokenIndex++] = currentToken;
					currentToken = "";
					insideTag = false;
				}
			} else if (insideNumber) {
				if (Character.isDigit(currentChar) || currentChar == '.' || currentChar == 'e' || currentChar == 'E' ||
						(currentChar == '-' && exponent)) {
					currentToken += currentChar;
					if (currentChar == '.' && !decimalPoint) {
						decimalPoint = true;
					}
					if (currentChar == 'e' || currentChar == 'E') {
						exponent = true;
					}
				} else {
					// End of number
					result[tokenIndex++] = currentToken;
					currentToken = "";
					insideNumber = false;
					exponent = false;
					decimalPoint = false;
					//re evaluate the current character
					i--;
				}
			} else {
				if (currentChar == '<') {
					// If there is a current word or number being built, add it before starting a
					// tag
					if (currentToken.length() > 0) {
						result[tokenIndex++] = currentToken;
						currentToken = "";
						hyphenUsed = false;
					}
					insideTag = true;
					currentToken += currentChar;
				} else if (isAlphabetic(currentChar)) {
					currentToken += currentChar;
				} else if (currentChar == '-' && currentToken.length() > 0
						&& isAlphabetic(currentToken.charAt(currentToken.length() - 1)) &&
						i + 1 < str.length() && isAlphabetic(str.charAt(i + 1)) && !hyphenUsed) {
					currentToken += currentChar;
					hyphenUsed = true;
				} else if (isPunctuation(currentChar)) {
					// If a word is being built, add it to tokens
					if (currentToken.length() > 0) {
						result[tokenIndex++] = currentToken;
						currentToken = "";
						hyphenUsed = false;
					}
					// Add punctuation as a separate token
					result[tokenIndex++] = String.valueOf(currentChar);
				} else if (Character.isDigit(currentChar) ||
						(currentChar == '-' && i + 1 < str.length() && Character.isDigit(str.charAt(i + 1)))) {
					// Start of a number
					if (currentToken.length() > 0) {
						result[tokenIndex++] = currentToken;
						currentToken = "";
						hyphenUsed = false;
					}
					insideNumber = true;
					currentToken += currentChar;
					if (currentChar == '-') {
						numberNegative = true;
					}
				} else {
					//any uncaught token
					if (currentToken.length() > 0) {
						result[tokenIndex++] = currentToken;
						currentToken = "";
						hyphenUsed = false;
						insideNumber = false;
						exponent = false;
						decimalPoint = false;
					}
				}
			}
		}

		// Add the last token if any
		if (currentToken.length() > 0) {
			result[tokenIndex++] = currentToken;
		}

		// Create a new array with the exact number of tokens
		String[] finalTokens = new String[tokenIndex];
		for (int j = 0; j < tokenIndex; j++) {
			finalTokens[j] = result[j];
		}

		return finalTokens;
	}

	/**
	 * Helper method to determine if a character is alphabetic (A-Z or a-z).
	 *
	 * @param c The character to check.
	 * @return True if alphabetic, else false.
	 */
	private boolean isAlphabetic(char c) {
		return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
	}

	/**
	 * Helper method to determine if a character is a punctuation character.
	 *
	 * @param c The character to check.
	 * @return True if punctuation, else false.
	 */
	private boolean isPunctuation(char c) {
		char[] punctuation = { '.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-' };
		for (int i = 0; i < punctuation.length; i++) {
			if (c == punctuation[i]) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Print the tokens in the array to the screen
	 * Precondition: All elements in the array are valid String objects.
	 * (no nulls)
	 * 
	 * @param tokens an array of String tokens
	 */
	public void printTokens(String[] tokens) {
		if (tokens == null)
			return;
		for (int a = 0; a < tokens.length; a++) {
			if (a % 5 == 0)
				System.out.print("\n  ");
			System.out.print("[token " + a + "]: " + tokens[a] + " ");
		}
		System.out.println();
	}

	/**
	 * @param result The array of all the tokens that is not properly sized.
	 * @return An array that removes all null values in the array.
	 */
	private String[] getSizedTokens(String[] result) {
		int count = 0;
		for (String s : result) {
			if (s != null) {
				count++;
			}
		}
		String[] sizedTokens = new String[count];
		int index = 0;
		for (String s : result) {
			if (s != null) {
				sizedTokens[index++] = s;
			}
		}
		return sizedTokens;
	}
}