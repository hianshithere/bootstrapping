package com.practice.bootstrapping.exception;

public class NoDataFoundInBootstrapResponse extends RuntimeException {
    public NoDataFoundInBootstrapResponse(String msg) {
        super(msg);
    }
}