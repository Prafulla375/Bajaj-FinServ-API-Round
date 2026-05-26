package com.bajaj.finserv.service;

import com.bajaj.finserv.dto.BfhlRequest;
import com.bajaj.finserv.dto.BfhlResponse;

/**
 * Service interface for processing /bfhl requests.
 * Provides the contract for classifying input data arrays.
 */
public interface BfhlService {

    /**
     * Processes the input data array and returns the classified response.
     *
     * @param request the incoming request containing the data array
     * @return the classified response with even/odd numbers, alphabets, special chars, etc.
     */
    BfhlResponse processData(BfhlRequest request);
}
