/**
 * Utilities for handling HTML, tokenizes tags, words, numbers, and punctuation,
 * and handles comments and preformatted text.
 *
 * @author Aditya Dendukuri
 * @since November 1 2024
 */
public class HTMLUtilities {

	// NONE = not nested in a block, COMMENT = inside a comment block
	// PREFORMAT = inside a pre-format block
	private enum TokenState {
		NONE, COMMENT, PREFORMAT
	};

	private TokenState state = TokenState.NONE;

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
		String[] result = new String[10000];
		int tokenIndex = 0;
		String currentToken = "";
		boolean insideTag = false;
		boolean hyphenUsed = false;
		boolean insideNumber = false;

		int i = 0;

		if (state == TokenState.COMMENT) {
			int endCommentIndex = str.indexOf("-->");
			if (endCommentIndex != -1) {
				i = endCommentIndex + 3;
				state = TokenState.NONE;
			} else {
				return new String[0]; // Entire line is inside comment
			}
		}

		if (state == TokenState.PREFORMAT) {
			if (equalsAtPosition(str.trim(), 0, "</pre>")) {
				result[tokenIndex++] = "</pre>";
				state = TokenState.NONE;
			} else {
				result[tokenIndex++] = str;
			}
			String[] finalTokens = new String[tokenIndex];
			for (int j = 0; j < tokenIndex; j++) {
				finalTokens[j] = result[j];
			}
			return finalTokens;
		}

		while (i < str.length()) {
			if (matchesAtPosition(str, i, "<!--")) {
				if (currentToken.length() > 0) {
					result[tokenIndex++] = currentToken;
					currentToken = "";
				}
				int endCommentIndex = str.indexOf("-->", i + 4);
				if (endCommentIndex != -1) {
					i = endCommentIndex + 3;
				} else {
					state = TokenState.COMMENT;
					i = str.length();
				}
			} else if (matchesAtPosition(str, i, "<pre>")) {
				if (currentToken.length() > 0) {
					result[tokenIndex++] = currentToken;
					currentToken = "";
				}
				result[tokenIndex++] = "<pre>";
				state = TokenState.PREFORMAT;
				i += 5;
				i = str.length(); // Exit loop, rest of line is preformatted
			} else if (matchesAtPosition(str, i, "</pre>")) {
				if (currentToken.length() > 0) {
					result[tokenIndex++] = currentToken;
					currentToken = "";
				}
				result[tokenIndex++] = "</pre>";
				state = TokenState.NONE;
				i += 6;
			} else {
				char currentChar = str.charAt(i);

				if (insideTag) {
					currentToken += currentChar;
					if (currentChar == '>') {
						result[tokenIndex++] = currentToken;
						currentToken = "";
						insideTag = false;
					}
					i++;
				} else if (insideNumber) {
					if (Character.isDigit(currentChar) || currentChar == '.' || currentChar == 'e' || currentChar == 'E'
							||
							(currentChar == '-' && i > 0 && (str.charAt(i - 1) == 'e' || str.charAt(i - 1) == 'E'))) {
						currentToken += currentChar;
						i++;
					} else {
						result[tokenIndex++] = currentToken;
						currentToken = "";
						insideNumber = false;
					}
				} else {
					if (currentChar == '<') {
						if (currentToken.length() > 0) {
							result[tokenIndex++] = currentToken;
							currentToken = "";
							hyphenUsed = false;
						}
						insideTag = true;
						currentToken += currentChar;
						i++;
					} else if (isAlphabetic(currentChar)) {
						currentToken += currentChar;
						i++;
					} else if (currentChar == '-' && currentToken.length() > 0
							&& isAlphabetic(currentToken.charAt(currentToken.length() - 1)) &&
							i + 1 < str.length() && isAlphabetic(str.charAt(i + 1)) && !hyphenUsed) {
						currentToken += currentChar;
						hyphenUsed = true;
						i++;
					} else if (Character.isDigit(currentChar) ||
							(currentChar == '-' && i + 1 < str.length() && Character.isDigit(str.charAt(i + 1)))) {
						if (currentToken.length() > 0) {
							result[tokenIndex++] = currentToken;
							currentToken = "";
							hyphenUsed = false;
						}
						insideNumber = true;
						currentToken += currentChar;
						i++;
					} else if (isPunctuation(currentChar)) {
						if (currentToken.length() > 0) {
							result[tokenIndex++] = currentToken;
							currentToken = "";
							hyphenUsed = false;
						}
						result[tokenIndex++] = String.valueOf(currentChar);
						i++;
					} else {
						if (currentToken.length() > 0) {
							result[tokenIndex++] = currentToken;
							currentToken = "";
							hyphenUsed = false;
						}
						i++;
					}
				}
			}
		}

		if (currentToken.length() > 0) {
			result[tokenIndex++] = currentToken;
		}

		String[] finalTokens = new String[tokenIndex];
		for (int j = 0; j < tokenIndex; j++) {
			finalTokens[j] = result[j];
		}

		return finalTokens;
	}

	/**
	 * Helper method to check if the substring starting at the specified index
	 * matches the given string.
	 *
	 * @param str     the string to check against
	 * @param index   the starting index in the string
	 * @param toMatch the string to match
	 * @return true if the substring matches the given string, false otherwise
	 */
	private boolean matchesAtPosition(String str, int index, String toMatch) {
		int strLen = str.length();
		int matchLen = toMatch.length();
		if (index + matchLen > strLen) {
			return false;
		}
		for (int j = 0; j < matchLen; j++) {
			if (str.charAt(index + j) != toMatch.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Helper method to check if the trimmed string equals the specified string
	 * starting at the given index.
	 *
	 * @param str     the string to check against
	 * @param index   the starting index in the trimmed string
	 * @param toMatch the string to match
	 * @return true if the trimmed substring equals the given string, false
	 *         otherwise
	 */
	private boolean equalsAtPosition(String str, int index, String toMatch) {
		String trimmedStr = str.trim();
		int strLen = trimmedStr.length();
		int matchLen = toMatch.length();
		if (index + matchLen > strLen) {
			return false;
		}
		for (int j = 0; j < matchLen; j++) {
			if (trimmedStr.charAt(index + j) != toMatch.charAt(j)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Helper method to determine if a character is alphabetic (A-Z or a-z).
	 *
	 * @param c The character to check.
	 * @return True if alphabetic, else false.
	 */
	private boolean isAlphabetic(char c) {
		return Character.isLetter(c);
	}

	/**
	 * Helper method to determine if a character is a punctuation character.
	 *
	 * @param c The character to check.
	 * @return True if punctuation, else false.
	 */
	private boolean isPunctuation(char c) {
		char[] punctuation = { '.', ',', ';', ':', '(', ')', '?', '!', '=', '&', '~', '+', '-' };
		for (char p : punctuation) {
			if (c == p) {
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
}
