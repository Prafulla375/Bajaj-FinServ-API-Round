package com.bajaj.finserv.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Request DTO for the /bfhl POST endpoint.
 * Contains the input data array to be processed.
 */
public class BfhlRequest {

    @JsonProperty("data")
    private List<String> data;

    public BfhlRequest() {
    }

    public BfhlRequest(List<String> data) {
        this.data = data;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
