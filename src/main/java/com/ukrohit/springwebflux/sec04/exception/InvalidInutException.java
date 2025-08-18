package com.ukrohit.springwebflux.sec04.exception;

public class InvalidInutException extends RuntimeException{

    public InvalidInutException(String msg)
    {
        super(msg);
    }
}
