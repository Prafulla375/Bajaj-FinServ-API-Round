package com.bajaj.finserv.service;

import com.bajaj.finserv.dto.BfhlRequest;
import com.bajaj.finserv.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for BfhlServiceImpl.
 * Tests all classification logic including edge cases.
 */
@SpringBootTest
class BfhlServiceImplTest {

    @Autowired
    private BfhlService bfhlService;

    // ===================== Example A from the assignment =====================

    @Test
    @DisplayName("Example A: Mixed data with numbers, alphabets, and special chars")
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertNotNull(response.getUserId());
        assertNotNull(response.getEmail());
        assertNotNull(response.getRollNumber());

        // Odd numbers: 1
        assertEquals(List.of("1"), response.getOddNumbers());

        // Even numbers: 334, 4
        assertEquals(List.of("334", "4"), response.getEvenNumbers());

        // Alphabets: A, R (uppercased)
        assertEquals(List.of("A", "R"), response.getAlphabets());

        // Special characters: $
        assertEquals(List.of("$"), response.getSpecialCharacters());

        // Sum: 1 + 334 + 4 = 339
        assertEquals("339", response.getSum());

        // Concat string: chars are a, R -> reversed: R, a -> alternating caps: R, a -> "Ra"
        assertEquals("Ra", response.getConcatString());
    }

    // ===================== Example B from the assignment =====================

    @Test
    @DisplayName("Example B: Multiple numbers, alphabets, and special chars")
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());

        // Odd numbers: 5
        assertEquals(List.of("5"), response.getOddNumbers());

        // Even numbers: 2, 4, 92
        assertEquals(List.of("2", "4", "92"), response.getEvenNumbers());

        // Alphabets: A, Y, B (uppercased)
        assertEquals(List.of("A", "Y", "B"), response.getAlphabets());

        // Special characters: &, -, *
        assertEquals(List.of("&", "-", "*"), response.getSpecialCharacters());

        // Sum: 2 + 4 + 5 + 92 = 103
        assertEquals("103", response.getSum());

        // Concat string: chars are a, y, b -> reversed: b, y, a -> alternating caps: B, y, A -> "ByA"
        assertEquals("ByA", response.getConcatString());
    }

    // ===================== Example C from the assignment =====================

    @Test
    @DisplayName("Example C: Multi-character alphabet strings only")
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());

        // No numbers
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());

        // Alphabets: A, ABCD, DOE (all uppercased)
        assertEquals(List.of("A", "ABCD", "DOE"), response.getAlphabets());

        // No special characters
        assertTrue(response.getSpecialCharacters().isEmpty());

        // Sum: 0 (no numbers)
        assertEquals("0", response.getSum());

        // Concat string: chars A,A,B,C,D,D,O,E -> reversed E,O,D,D,C,B,A,A
        // alternating caps: E,o,D,d,C,b,A,a -> "EoDdCbAa"
        assertEquals("EoDdCbAa", response.getConcatString());
    }

    // ===================== Edge Cases =====================

    @Test
    @DisplayName("Empty data array should return empty results with sum 0")
    void testEmptyDataArray() {
        BfhlRequest request = new BfhlRequest(Collections.emptyList());
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Null data array should return empty results")
    void testNullDataArray() {
        BfhlRequest request = new BfhlRequest(null);
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only numbers should produce correct sums and classification")
    void testOnlyNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("1", "2", "3", "100"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("1", "3"), response.getOddNumbers());
        assertEquals(List.of("2", "100"), response.getEvenNumbers());
        assertTrue(response.getAlphabets().isEmpty());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("106", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only special characters")
    void testOnlySpecialCharacters() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("@", "#", "$", "%"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(List.of("@", "#", "$", "%"), response.getSpecialCharacters());
        assertEquals("0", response.getSum());
        assertEquals("", response.getConcatString());
    }

    @Test
    @DisplayName("Only alphabets should produce correct concat_string")
    void testOnlyAlphabets() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "b", "c"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(List.of("A", "B", "C"), response.getAlphabets());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        // chars: a,b,c -> reversed: c,b,a -> alternating: C,b,A -> "CbA"
        assertEquals("CbA", response.getConcatString());
    }

    @Test
    @DisplayName("Zero should be classified as even")
    void testZeroIsEven() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("0"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertEquals(List.of("0"), response.getEvenNumbers());
        assertEquals("0", response.getSum());
    }

    @Test
    @DisplayName("Large numbers should be handled correctly")
    void testLargeNumbers() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("999999999", "1000000000"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertEquals(List.of("999999999"), response.getOddNumbers());
        assertEquals(List.of("1000000000"), response.getEvenNumbers());
        assertEquals("1999999999", response.getSum());
    }

    @Test
    @DisplayName("Mixed alphanumeric strings should be classified as special characters")
    void testMixedAlphanumericAsSpecial() {
        // "a1" is neither purely numeric nor purely alphabetic -> special character
        BfhlRequest request = new BfhlRequest(Arrays.asList("a1", "2b", "3c4"));
        BfhlResponse response = bfhlService.processData(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertTrue(response.getAlphabets().isEmpty());
        assertEquals(List.of("a1", "2b", "3c4"), response.getSpecialCharacters());
        // But concat_string still extracts alphabetical characters: a, b, c
        // reversed: c, b, a -> alternating: C, b, A -> "CbA"
        assertEquals("CbA", response.getConcatString());
    }

    @Test
    @DisplayName("Response should contain correct user details")
    void testUserDetails() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("1"));
        BfhlResponse response = bfhlService.processData(request);

        assertNotNull(response.getUserId());
        assertNotNull(response.getEmail());
        assertNotNull(response.getRollNumber());
        assertTrue(response.getUserId().contains("prafulla_dongre"));
        assertEquals("prafulladongre230508@acropolis.in", response.getEmail());
        assertEquals("0827CS231183", response.getRollNumber());
    }
}
