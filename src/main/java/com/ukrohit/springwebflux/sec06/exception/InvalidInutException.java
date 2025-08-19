package com.ukrohit.springwebflux.sec06.exception;

public class InvalidInutException extends RuntimeException{

    public InvalidInutException(String msg)
    {
        super(msg);
    }
}
