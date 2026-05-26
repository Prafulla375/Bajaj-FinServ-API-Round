package com.bajaj.finserv.controller;

import com.bajaj.finserv.dto.BfhlRequest;
import com.bajaj.finserv.dto.BfhlResponse;
import com.bajaj.finserv.service.BfhlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for the /bfhl endpoint.
 * Handles POST requests to process data arrays.
 */
@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    /**
     * POST /bfhl - Processes the input data array and returns classified results.
     *
     * @param request the request body containing the data array
     * @return 200 OK with the classified response
     */
    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@RequestBody BfhlRequest request) {
        BfhlResponse response = bfhlService.processData(request);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /bfhl - Returns a simple operation code (useful for health checks).
     *
     * @return 200 OK with operation code 1
     */
    @GetMapping
    public ResponseEntity<String> getOperationCode() {
        return ResponseEntity.ok("{\"operation_code\": 1}");
    }
}
