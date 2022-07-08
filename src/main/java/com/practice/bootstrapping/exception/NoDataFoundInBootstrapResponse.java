package com.practice.bootstrapping.exception;

public class NoDataFoundInBootstrapResponse extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoDataFoundInBootstrapResponse(String msg) {
        super (msg);
    }
}