package it.kata.calculator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class StringCalculatorTest {

	@Test
	public void assertZeroEqualEmptyString() {
		assertEquals(0, StringCalculator.add(""), "ZeroEqualEmptyString not working");
	}

	@Test
	public void assertSimpleSum() {
		assertEquals(6, StringCalculator.add("1,2,3"), "SimpleSum not working");
	}

	@Test
	public void assertSimpleSumNotWork() {
		assertNotEquals(10, StringCalculator.add("1,2,3"), "SimpleSumNotWorking not working");
	}

	@Test
	public void assertNewLine() {
		assertEquals(15, StringCalculator.add("1,2,3\n4,5"), "NewLine not working");
	}

	@Test
	public void assertDifferentDivider() {
		assertEquals(15, StringCalculator.add("//;\n1;2;3\n4;5"), "DifferentDivider not working");
	}

	@Test
	public void assertNoNegative() {
		assertThrows(RuntimeException.class, () -> StringCalculator.add("//;\n1;-2;-3\n4;5"), "NoNegative not working");
	}

	@Test
	public void assertNoBigNumbers() {
		assertEquals(1000, StringCalculator.add("3000\n1000,1001"), "NoBigNumbers not working");
	}

	@Test
	public void assertGetCalledCount() {
		assertNotEquals(-1, StringCalculator.getCalledCount(), "GetCalledCount not working");
	}

	@Test
	public void assertExtraConfig() {
		assertEquals(6, StringCalculator.add("//[***]\n1***2***3"), "ExtraConfig not working");
	}

	@Test
	public void assertExtraConfigMultiple() {
		assertEquals(15, StringCalculator.add("//[***][@@]\n5***2@@3@@5"), "ExtraConfigMultiple not working");
	}
}
