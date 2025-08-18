package com.ukrohit.springwebflux.sec05.exception;

public class InvalidInutException extends RuntimeException{

    public InvalidInutException(String msg)
    {
        super(msg);
    }
}
