package com.bajaj.finserv.dto;

public class SqlSubmission {

    private String finalQuery;

    public SqlSubmission(String finalQuery) {
        this.finalQuery = finalQuery;
    }

    public String getFinalQuery() { return finalQuery; }
    public void setFinalQuery(String finalQuery) { this.finalQuery = finalQuery; }
}
