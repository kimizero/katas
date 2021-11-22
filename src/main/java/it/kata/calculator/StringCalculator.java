package it.kata.calculator;

import java.util.ArrayList;
import java.util.List;

/**
 * String calculator Kata. </br>
 * </br>
 * 
 * @see <a href=
 *      "https://static1.squarespace.com/static/5c741968bfba3e13975e33a6/t/5ca6614d971a1877cadc4f8a/1554407757512/String+Calculator+Kata+v1.pdf">Full
 *      description PDF</a>
 * 
 * @author andlazze
 */
public class StringCalculator {
	private static int callCount = 0;

	/**
	 * Method for adding positive numbers. Numbers smaller than 1000 will not be
	 * considered. Negative numbers will throw an exception.
	 * 
	 * @param numbers the string with the numbers. It must be formatted according to
	 *                the Kata rules.
	 * @return the sum of the numbers
	 */
	public static int add(String numbers) {
		// Increase number of time this method was called
		callCount++;

		// Check if the input is empty. If it is then the sum is zero
		if (numbers == null || numbers.isBlank()) {
			return 0;
		}

		// The new line separator is always available so the list is initialized with it
		List<String> separators = new ArrayList<>();
		separators.add("\n");

		int startPos = 0;

		// If there's a configuration for the separator, read it and use it
		if (numbers.startsWith("//")) {
			char c = numbers.charAt(2);

			// First case: the configuration is simple and immediately declare the separator
			if (c != '[') {
				separators.add(String.valueOf(numbers.charAt(2)));
				startPos = 4;
			} else {
				// Second case: the configuration is complex and it's needed to split the items
				startPos = 3;
				int i = 3;
				c = numbers.charAt(3);

				while (c != '\n') {
					switch (c) {
					case '[':
						startPos = i + 1;
						break;
					case ']':
						separators.add(numbers.substring(startPos, i));
						break;
					default:
						break;
					}

					i++;
					c = numbers.charAt(i);
				}

				startPos = i + 1;
			}
		} else {
			// Default case separator
			separators.add(",");
		}

		List<Integer> valuesToAdd = new ArrayList<>();
		boolean findNumber = true;

		while (startPos < numbers.length()) {
			String token = findNextToken(numbers, startPos, findNumber);

			// If looking for a number, then it is added to the list, otherwise a check on
			// allowed separators is made
			if (findNumber) {
				valuesToAdd.add(Integer.valueOf(token));
			} else if (!separators.contains(token)) {
				throw new RuntimeException(String.format("Error: separator '%s' not allowed", token));
			}

			// Numbers and separators alternate, so after a number the code search for a
			// separator and vice-versa
			findNumber = !findNumber;
			startPos += token.length();
		}

		int sum = 0;
		String negative = "";

		// For all values in the string, if they are negative then add them to a string
		// that will be used as a message for the exception, else if they are smaller
		// than the 1000 limit then sum them together
		for (int val : valuesToAdd) {
			if (val < 0) {
				negative += val + ", ";
			} else if (val <= 1000) {
				sum += val;
			}
		}

		// If there are negatives, throw an exception
		if (!negative.isBlank()) {
			throw new RuntimeException("negatives not allowed: " + negative.substring(0, negative.length() - 2));
		}

		return sum;
	}

	/**
	 * Finds the next token in the given string. A token is defined either as a
	 * number or a separator.
	 * 
	 * @param numbers    the string from which a token needs to be extracted
	 * @param startPos   the starting position for the search algorithm. The token
	 *                   will start from this position.
	 * @param wantNumber if the method needs to find for a number or a separator.
	 * @return a token containing either a number or a separator
	 */
	private static String findNextToken(String numbers, int startPos, boolean wantNumber) {
		int i = 0;

		for (i = startPos; i < numbers.length(); i++) {
			char c = numbers.charAt(i);

			if (((c >= '0' && c <= '9') || c == '-') ^ wantNumber) {
				break;
			}
		}

		return numbers.substring(startPos, i);
	}

	/**
	 * Returns the number of times the method {@link StringCalculator#add(String)}
	 * is called in the same session
	 * 
	 * @return the number of calls
	 */
	public static int getCalledCount() {
		return callCount;
	}
}
