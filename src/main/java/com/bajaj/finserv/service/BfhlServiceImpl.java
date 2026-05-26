package com.bajaj.finserv.service;

import com.bajaj.finserv.dto.BfhlRequest;
import com.bajaj.finserv.dto.BfhlResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Implementation of BfhlService.
 * Handles classification of input data into numbers (even/odd),
 * alphabets, special characters, and computes sum + concat_string.
 */
@Service
public class BfhlServiceImpl implements BfhlService {

    @Value("${bfhl.user.id}")
    private String userId;

    @Value("${bfhl.user.email}")
    private String email;

    @Value("${bfhl.user.rollNumber}")
    private String rollNumber;

    @Override
    public BfhlResponse processData(BfhlRequest request) {
        BfhlResponse response = new BfhlResponse();

        List<String> data = request.getData();

        // Validate input
        if (data == null || data.isEmpty()) {
            response.setSuccess(true);
            response.setUserId(userId);
            response.setEmail(email);
            response.setRollNumber(rollNumber);
            response.setOddNumbers(Collections.emptyList());
            response.setEvenNumbers(Collections.emptyList());
            response.setAlphabets(Collections.emptyList());
            response.setSpecialCharacters(Collections.emptyList());
            response.setSum("0");
            response.setConcatString("");
            return response;
        }

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        long sum = 0;

        for (String item : data) {
            if (item == null || item.isEmpty()) {
                continue;
            }

            if (isNumeric(item)) {
                // Parse number and classify as even/odd
                long number = Long.parseLong(item);
                sum += number;
                if (number % 2 == 0) {
                    evenNumbers.add(item);
                } else {
                    oddNumbers.add(item);
                }
            } else if (isAlphabetic(item)) {
                // Convert to uppercase and add to alphabets
                alphabets.add(item.toUpperCase());
            } else {
                // Everything else is a special character
                specialCharacters.add(item);
            }
        }

        // Build concat_string: collect all alphabetical characters from input,
        // reverse their order, then apply alternating caps (upper, lower, upper, ...)
        String concatString = buildConcatString(data);

        response.setSuccess(true);
        response.setUserId(userId);
        response.setEmail(email);
        response.setRollNumber(rollNumber);
        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(String.valueOf(sum));
        response.setConcatString(concatString);

        return response;
    }

    /**
     * Checks if the entire string is numeric (digits only, optionally with leading minus).
     * Only pure digit strings are considered numbers (no negative signs per examples).
     */
    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    /**
     * Checks if the entire string consists of only alphabetical characters.
     */
    private boolean isAlphabetic(String str) {
        return str.matches("[a-zA-Z]+");
    }

    /**
     * Builds the concat_string by:
     * 1. Extracting all individual alphabetical characters from the input data (in order)
     * 2. Reversing the collected characters
     * 3. Applying alternating caps: index 0 = uppercase, index 1 = lowercase, ...
     */
    private String buildConcatString(List<String> data) {
        StringBuilder allChars = new StringBuilder();

        for (String item : data) {
            if (item == null) continue;
            for (char c : item.toCharArray()) {
                if (Character.isLetter(c)) {
                    allChars.append(c);
                }
            }
        }

        // Reverse the collected characters
        String reversed = allChars.reverse().toString();

        // Apply alternating caps
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (i % 2 == 0) {
                result.append(Character.toUpperCase(c));
            } else {
                result.append(Character.toLowerCase(c));
            }
        }

        return result.toString();
    }
}
